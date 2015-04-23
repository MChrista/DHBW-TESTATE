package cg.draw2D;

// Fuer Controller
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.Dimension;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;

import cg.controls.CGSlider;
import cg.controls.CGButton;
import cg.controls.CGSliderListener;
import cg.controls.CircleListener;
import cg.controls.ClearButtonListener;
import cg.controls.ClipListener;
import cg.controls.LineListener;
import cg.controls.PointListener;
import cg.controls.PolygonListener;
import cg.controls.RectangleListener;
import cg.controls.PickListener;
import cg.controls.StuetzpunktListener;
import cg.controls.MoveListener;
import cg.controls.BSplinePane;
import cg.controls.SplinePane;
import cg.controls.BezierPane;
import cg.controls.TextField;
import cg.controls.PanelButton;
import cg.controls.IterButtonListener;
import cg.controls.SaveButtonListener;

import cg.tools.DitherMatrix;
import cg.tools.OrderedDitherMatrix;
import cg.tools.Resetable;
import cg.tools.WhiteNoiseDither;
import cg.tools.MatrixManager;

import cg.grOb.Circle;
import cg.grOb.Koch;
import cg.grOb.Baum;
import cg.grOb.LSystem;

/**
 * Controller der Applikation. Kennt die View und das Model.
 * Hier werden alle Komponenten erstellt und mit den zugehoerigen Listenern
 * verknuepft.
 *
 * @version 1.5 (A 18.05.2004)
 * @author Ralf Kunze
 */
public class Controller extends JPanel implements Observer, Resetable {
   private View view; // die View (Zeichenflaeche)
   private Model model; // das Model
   private Vector elv; // Vektor mit MouseInputListenern
   final static int SEL = 0; // anfangs ist Item 0 selektiert
   final static int SEL_DITHER = 0; // anfangs ist Item 0 selektiert
   private JComboBox choice;
   private JComboBox ditherChoice;

   final static int WIDTH = 400;
   final static int HEIGHT = 300;

   /**
    * Erzeugt eine Instanz der Klasse Controller. Das Objekt repraesentiert
    * ein JPanel und darin sind alle notwendigen GUI-Elemente enthalten.
    */
   public Controller() {

      setView(new View()); // View vermerken

      DitherMatrix[] dm = new DitherMatrix[3];
      dm[0] = null;
      dm[1] = new OrderedDitherMatrix(Model.DITHERDIM);
      dm[2] = new WhiteNoiseDither(Model.MAX_GRAUWERT);
      setModel(new Model(dm)); // Model erzeugen und merken
      registerWithModel(); // Beim Model als Observer
      add(createComponents(), BorderLayout.CENTER);
      getView().display((getModel()).getComponent());
   }

   /**
    * In dieser Methode wird die GUI zusammengebaut. Dies wurde der &Uuml;bersichtlichkeit
    * halber ausgegliedert.
    * 
    * @return Die GUI der Applikation
    */
   private Component createComponents() {

      JPanel pane = new JPanel(); // Hier kommt alles rein
      JPanel tmp = new JPanel(); // Hilfsplatzhalter
      JPanel right = new JPanel(new BorderLayout());

      // Aussen etwas Platz schaffen
      pane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
      pane.add(getView()); // View view einhaengen

      //CardLayout erzeugen.
      JTabbedPane cards = new JTabbedPane();

      // Die einzelnen Cards einhaengen
      cards.add("Draw2D", createDraw2D());

      JPanel transform = new JPanel();
      MatrixManager mm = new MatrixManager(3);
      transform.add(mm);
      cards.add("Transform", transform);

      JPanel curves = new JPanel();
      curves.setLayout(new GridLayout(4, 1, 10, 10));

      BezierPane bp = new BezierPane(getModel());
      SplinePane sp = new SplinePane(getModel());
      BSplinePane bsp = new BSplinePane(getModel());

      curves.add(bp);
      curves.add(sp);
      curves.add(bsp);
      curves.add(createStuetzpunktChoice());

      cards.add("Kurven", curves);

      // Panel fuer polynomiale Fraktale
      JPanel poly = new JPanel();
      poly.setLayout(new GridLayout(5, 1, 20, 20));

      JPanel fuertext = new JPanel(); // Nur wegen der Optik
      TextField regel = new TextField("rurd"); // Textfeld für die Regel des L-Systems
      regel.setPreferredSize(new Dimension(100, 30)); // Default Regel wird mitgegeben
      //setRegelmodel(regel.getDocument()); // Regelmodel verwaltet Regel des L-Systems
      fuertext.setLayout(new FlowLayout());
      fuertext.add(new JLabel("Regel für r: "));
      fuertext.add(regel);
      // Panel zur Auswahl der Art der fraktalen Kurve
      PanelButton iter = new PanelButton("iteriere");
      JPanel auswahl = createFractFunctionChoice(regel); //haengt sich automatisch an die letzte Stelle des Arrays ein
      iter.addActionListener(new IterButtonListener(getModel()));

      poly.add(auswahl);
      poly.add(iter);
      poly.add(fuertext);

      cards.add("Fraktale", poly);
      
      JPanel file = new JPanel();
      CGButton save = new CGButton("Save to SVG", 
                                   "Bitte hier druecken, um den aktuellen Stand der Applikation als SVG zu speichern", 
                                   "Save", "Save", new SaveButtonListener(model,view));
      file.add(save);
      cards.add("SVG", file);
      MouseInputAdapter[] mia = { new PointListener(getModel()), new PickListener(getModel(), mm), new StuetzpunktListener(getModel()), null, null };

      cards.addChangeListener(new TabListener(view, cards, mia));
      //Die Tabbed Pane einhaengen
      right.add(cards, BorderLayout.NORTH);

      tmp = new JPanel();
      tmp.setLayout(new GridLayout(2, 1));

      // Slider fuer den Factor einhaengen
      CGSlider zoom = new CGSlider("Zoomfaktor", 1, 10, 1, new ZoomFactorListener(getModel()));
      tmp.add(zoom); // Zoomschieber einhaengen

      // ClearButton einhaengen
      JButton clear = new JButton("Clear");
      clear.setSize(new Dimension(80, 25));
      ClearButtonListener cbl = new ClearButtonListener();
      
      // Resetables an den Listener uebergeben
      cbl.append(model);
      cbl.append(zoom);
      cbl.append(mm);
      cbl.append(bp);
      cbl.append(sp);
      cbl.append(bsp);
      clear.addActionListener(cbl);

      tmp.add(clear);
      tmp.setBorder(BorderFactory.createEtchedBorder());
      right.add(tmp, BorderLayout.SOUTH);

      pane.add(right);
      return pane;
   }

   /**
    * Erzeugt die GUI Komponenten fuer die Erstellund der 2D Objekte.
    * @return Die erstellten GUI Komponenten in einem JPanel.
    */
   private JPanel createDraw2D() {

      // Panel fuer die Draw2D Controls erzeugen
      JPanel draw2D = new JPanel();
      draw2D.setLayout(new GridLayout(4, 1, 20, 20)); // RasterLayout

      draw2D.add(choice = createFunctionChoice(), BorderLayout.NORTH); // Auswahl erzeugen/einhaengen
      draw2D.add(ditherChoice = createDitherChoice(), BorderLayout.CENTER);

      // Slider fuer den Grauwerteinhaengen
      CGSlider grey = new CGSlider("Dither-Grauwert", 0, Model.MAX_GRAUWERT - 1, 0, new GreyFactorListener(getModel()));
      draw2D.add(grey, BorderLayout.SOUTH); // Grauwertschieber einhaengen
      draw2D.setBorder(BorderFactory.createEtchedBorder());
      JPanel tmp = new JPanel();
      tmp.add(draw2D);
      return tmp;
   }

   /**
    * Erzeugt eine ComboBox mit der Auswahl, was fuer ein Fraktal erstellt werden soll
    * @return JPanel mit der ComboBox.
    */
   private JPanel createFractFunctionChoice(TextField regel) {
      Vector lv = new Vector(1, 1); // Vektor mit EventListenern
      lv.addElement("");
      lv.addElement("Koch");
      lv.addElement("Baum");
	  lv.addElement("Zufallsbaum");
      lv.addElement("L-System");

      JComboBox choice = new JComboBox(lv); // ComboBox erzeugen
      choice.setPreferredSize(new Dimension(150, 20));
      choice.setSelectedIndex(SEL); // Punkt ist aktiv
      choice.setToolTipText("Bitte waehlen Sie aus, was fuer ein Fraktal Sie erstellen moechten");
      choice.addItemListener(new FractFuncListener(getModel(),regel));
      JPanel pane = new JPanel(new FlowLayout());
      pane.setBorder(BorderFactory.createEtchedBorder());
      pane.add(new JLabel("Fraktal: "));
      pane.add(choice);
      return pane;
   }

   /**
    * Erzeugt eine ComboBox mit der Auswahl, ob Stuetzpunklte gesetzt oder verschoben
    * werden sollen.
    * @return JPanel mit der ComboBox.
    */
   private JPanel createStuetzpunktChoice() {
      Vector lv = new Vector(1, 1); // Vektor mit EventListenern
      lv.addElement(new StuetzpunktListener(getModel()));
      lv.addElement(new MoveListener(getModel()));

      JComboBox choice = new JComboBox(lv); // ComboBox erzeugen
      choice.setPreferredSize(new Dimension(150, 20));
      choice.setSelectedIndex(SEL); // Punkt ist aktiv
      choice.setToolTipText("Bitte waehlen Sie aus, ob sie Stuetzpunkte ziehen oder setzen moechten");
      // ItemListener erzeugen
      choice.addItemListener(new StuetzpunktChoiceListener(getView(), lv, SEL));
      JPanel pane = new JPanel(new FlowLayout());
      pane.setBorder(BorderFactory.createEtchedBorder());
      pane.add(new JLabel("Funktion"));
      pane.add(choice);
      return pane;
   }

   /**
    * Eine Instanz dieser Klasse haengt den jeweils gewaehlten MouseListener
    * bei der View ein.
    * @author Ralf Kunze
    */
   protected static class StuetzpunktChoiceListener implements ItemListener {
      private View view;
      private Vector lv; // Vector mit Listenern

      /**
       * Erzeugt einen FunktionListener.
       * @param view die View der Applikation. Sie bekommt einen neuen
       * Listener registriert.
       * @param lv Vektor mit den Listenern, die ggfs aktiviert werden
       * koennen.
       * @param sel der sel-te Listener in lv soll initial aktiviert sein
       */
      public StuetzpunktChoiceListener(View view, Vector lv, int sel) {
         this.view = view;
         this.lv = lv;
      }

      /**
       * Wird aufgerufen, wenn der user in der ComboBox einen neuen Wert
       * ausgewaehlt hat.
       * @param e Event, der vom User ausgeloest wurde.
       */
      public void itemStateChanged(ItemEvent e) {

         MouseListener[] ml = view.getMouseListeners();

         JComboBox cb = (JComboBox) e.getSource(); // Verbindng herstellen
         int index = cb.getSelectedIndex(); // Pos. des sel. Items

         for (int i = 0; i < ml.length; i++) { // Alle alten Listener weg
            view.removeMouseListener((MouseListener) ml[i]);
            view.removeMouseMotionListener((MouseMotionListener) ml[i]);
         }
         // gewaehlten neuen Listener dazu.
         view.addMouseListener((MouseListener) lv.elementAt(index));
         view.addMouseMotionListener((MouseMotionListener) lv.elementAt(index));
      }
   }


   /**
    * Eine Instanz dieser Klasse haengt den jeweils gewaehlten MouseListener
    * bei der View ein.
    * @author Ralf Kunze
    * @version 1.0 (A 17.05.2004)
    */
   protected static class FractFuncListener implements ItemListener {

      private Model m;
      private View view;
      TextField regel;
      
      /**
       * Erzeugt einen FunktionListener.
       */
      public FractFuncListener(Model m, TextField regel) {
         if (m==null) System.err.println("MODEL==NULL!!");
         this.m=m;
         this.regel = regel;
      }

      /**
       * Wird aufgerufen, wenn der user in der ComboBox einen neuen Wert
       * ausgewaehlt hat.
       * @param e Event, der vom User ausgeloest wurde.
       */
      public void itemStateChanged(ItemEvent e) {
         JComboBox cb = (JComboBox) e.getSource(); // Verbindung herstellen
         int index = cb.getSelectedIndex(); // Pos. des sel. Items
         m.removeFractal();
         LSystem ls;
         switch (index) {
            case 0: break;
            case 1: m.setFractal(new Koch(View.WIDTH, View.HEIGHT, 100000)); break;
            case 2: m.setFractal(new Baum(View.WIDTH, View.HEIGHT, 100000, false)); break;
			case 3: m.setFractal(new Baum(View.WIDTH, View.HEIGHT, 100000, true)); break;
            case 4: m.setFractal(ls = new LSystem(View.WIDTH, View.HEIGHT, 100000)); ls.setRegel(regel.getText()); break;
         }
      }
   }

   /**
    * In dieser Methode wird die Funktionsauswahl zusammengebaut.
    * 
    * @return Eine Combobox mit den entsprechenden Eintraegen.
    */
   private JComboBox createFunctionChoice() {
      Vector lv = new Vector(1, 1); // Vektor mit EventListenern
      lv.addElement(new PointListener(getModel()));
      lv.addElement(new LineListener(getModel()));
      lv.addElement(new RectangleListener(getModel()));
      lv.addElement(new CircleListener(getModel()));
      lv.addElement(new CircleListener(getModel(), "Gefuellter Kreis", Circle.FILLED));
      lv.addElement(new PolygonListener(getModel(), 10));
      lv.addElement(new ClipListener(getModel()));
      JComboBox choice = new JComboBox(lv);
      choice.setSelectedIndex(SEL);

      choice.addItemListener(new FunctionListener(getView(), lv, SEL));
      return choice;
   }

   /**
    * In dieser Methode wird die Funktionsauswahl zusammengebaut.
    * 
    * @return Eine Combobox mit den entsprechenden Eintraegen.
    */
   private JComboBox createDitherChoice() {
      Vector tv = new Vector(1, 1); // Vektor mit Eintragstexten
      tv.addElement("None");
      tv.addElement("OrderedDitherMatrix");
      tv.addElement("WhiteNoise Dithering");

      JComboBox choice = new JComboBox(tv); // ComboBox erzeugen
      choice.setSelectedIndex(SEL_DITHER); // Punkt ist aktiv
      // ItemListener erzeugen
      choice.addItemListener(new DitherListener(model));
      return choice;
   }

   /**
    * Setzt das Model des Controllers.
    * @param model das Model des MVC-Entwurfsmusters
    */
   public void setModel(Model model) {
      this.model = model;
   }

   /**
    * Liefert das Model des Controllers.
    * @return gibt das Model des MVC-Enwurfsmusters zurueck
    */
   public Model getModel() {
      return model;
   }

   /**
    * Setzt die View der Applikation.
    * @param view die zu merkende View
    */
   public void setView(View view) {
      this.view = view;
   }

   /**
    * Liefert die View des Controllers.
    * @return die gelieferte View
    */
   public View getView() {
      return view;
   }

   /**
    * Meldet den Controller als Observer beim Model an.
    */
   public void registerWithModel() {
      getModel().addObserver(this);
   }

   /** 
    * Schickt der View den Wert des Models, wenn dieser sich veraendert hat.
    *
    * @param sender Das Observable-Objekt, welches sich gaendert hat, in
    *               diesem Fall das Model.
    * @param arg Zusaetzliches Objekt, welches von der Methode
    *            <tt>notifyObservers()</tt> mitgeliefert werden kann; hat in
    *            diesem Fall keine Bedeutung
    */
   public void update(Observable sender, Object arg) {
      // Die View soll die Komponente darstellen, die das Model auf Nachfrage
      // liefert.
      getView().display(((Model) sender).getComponent());
   }

   /** 
    * Eine Instanz dieser Klasse setzt den ZoomFactor des Models auf den
    * Wert, den der entsprechende JScrollBar zeigt.
    */
   protected static class ZoomFactorListener extends CGSliderListener {
      private Model model;

      /**
       * Konstruktor des ZoomfaktorListeners
       * 
       * @param model Das Model der Applikation, welches den aktuellen Zoomfaktor
       * enthaelt
       */
      public ZoomFactorListener(Model model) {
         this.model = model;
      }

      /**
       * Wenn der Slider veraendert wird, wird dieses Event erzeugt
       *
       * @param e Erzeugtes Event
       */
      public void stateChanged(ChangeEvent e) {
         model.setFactor(((JSlider) e.getSource()).getValue());
      }
   }

   /** 
    * Eine Instanz dieser Klasse setzt den ZoomFactor des Models auf den
    * Wert, den der entsprechende JScrollBar zeigt.
    */
   protected static class GreyFactorListener extends CGSliderListener {
      private Model model;

      /**
       * Konstruktor des ZoomfaktorListeners
       * 
       * @param model Das Model der Applikation, welches den aktuellen Zoomfaktor
       * enthaelt
       */
      public GreyFactorListener(Model model) {
         this.model = model;
      }

      /**
       * Wenn der Slider veraendert wird, wird dieses Event erzeugt
       *
       * @param e Erzeugtes Event
       */
      public void stateChanged(ChangeEvent e) {
         model.setGreyValue(((JSlider) e.getSource()).getValue());
      }
   }

   /** 
   	* Eine Instanz dieser Klasse setzt den ZoomFactor des Models auf den
   	* Wert, den der entsprechende JScrollBar zeigt.
   	*/
   protected static class TabListener implements ChangeListener {
      private View view;
      private JTabbedPane jtp;
      private MouseInputAdapter[] listeners;
      private int oldTabIndex;

      /**
       *  Konstruktor des TabListeners
       * @param view Die View, bei der die Listner umgehaengt werden muessen
       * @param jtp Die JTabbep Pane, an der der Listener gehaengt wird.
       * @param listeners Die Listeners der View je nach aktueller Tab
       */
      public TabListener(View view, JTabbedPane jtp, MouseInputAdapter[] listeners) {
         this.view = view;
         this.jtp = jtp;
         this.listeners = listeners;
         oldTabIndex = jtp.getSelectedIndex();
      }

      /**
       * Wenn der Slider veraendert wird, wird dieses Event erzeugt
       *
       * @param e Erzeugtes Event
       */
      public void stateChanged(ChangeEvent e) {
         MouseListener[] ml = view.getMouseListeners();
         if (ml == null || ml.length == 0)
            listeners[oldTabIndex] = null;
         else
            listeners[oldTabIndex] = (MouseInputAdapter) ml[0];

         view.removeMouseListener((MouseListener) listeners[oldTabIndex]);
         view.removeMouseMotionListener((MouseMotionListener) listeners[oldTabIndex]);

         oldTabIndex = ((JTabbedPane) e.getSource()).getSelectedIndex();
         if (listeners[oldTabIndex] != null) {
            view.addMouseListener((MouseListener) listeners[oldTabIndex]);
            view.addMouseMotionListener((MouseMotionListener) listeners[oldTabIndex]);
         }
      }
   }

   /** 
    * Eine Instanz dieser Klasse haengt den jeweils gewaehlten MouseListener
    * bei der View ein.
    */
   protected static class FunctionListener implements ItemListener {
      private View view;
      private Vector lv; // Vector mit Listenern
      private Vector tv; // Vector mit Texten

      /**
       *  Konstrukter des FunktionListeners.
       * Erzeugt einen Listener, der bei einer View Listener austauschen kann.
       * 
       * @param view Die View , bei der die Listener ausgetauscht werden sollen
       * @param lv Vector mit den verschiedenen Listenern
       * @param sel Der aktuell selektierte Listener
       */
      public FunctionListener(View view, Vector lv, int sel) {
         this.view = view;
         this.lv = lv;
         view.addMouseListener((MouseListener) lv.elementAt(sel));
         view.addMouseMotionListener((MouseMotionListener) lv.elementAt(sel));
      }

      /**
       * Wenn in der ComboBox eine neue Auswahl getroffen wird, wird der Listener
       * entsprechend umgehaengt.
       * 
       *  @param e Erzeugtes Event
       */
      public void itemStateChanged(ItemEvent e) {
         JComboBox cb = (JComboBox) e.getSource();
         int index = cb.getSelectedIndex(); // Pos. des sel. Items

         MouseListener[] ml = view.getMouseListeners();
         for (int i = 0; i < ml.length; i++) { // Alle alten Listener weg
            view.removeMouseListener((MouseListener) ml[i]);
            view.removeMouseMotionListener((MouseMotionListener) ml[i]);
         }

         // gewaehlten neuen Listener dazu.
         view.addMouseListener((MouseListener) lv.elementAt(index));
         view.addMouseMotionListener((MouseMotionListener) lv.elementAt(index));
      }
   }

   /** 
    * Eine Instanz dieser Klasse haengt den jeweils gewaehlten MouseListener
    * bei der View ein.
    */
   protected static class DitherListener implements ItemListener {
      private Model m;

      /**
       *  Konstrukter des DitherListeners.
       * 
       * @param m Das zugehoerige Model
       */
      public DitherListener(Model m) {
         this.m = m;
      }

      /**
      * Wenn in der ComboBox eine neue Auswahl getroffen wird, wird der Listener
      * entsprechend umgehaengt.
      * 
      *  @param e Erzeugtes Event
      */
      public void itemStateChanged(ItemEvent e) {
         JComboBox cb = (JComboBox) e.getSource(); // Verbindng herstellen
         m.setDitherMode(cb.getSelectedIndex()); // Pos. des sel. Items
      }
   }

   /**
    * Methode des Interfaces Resetable.
    * Mittels dieser Methode wid die ComboBox in den Ursprungszustand versetzt.
    */
   public void reset() {
      choice.setSelectedIndex(SEL); // Punkt ist aktiv
      ditherChoice.setSelectedIndex(SEL_DITHER);
   }
}
