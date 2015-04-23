TARGETS = controls/BSplinePane.class controls/BezierPane.class controls/CGButton.class controls/CGSlider.class controls/CGSliderListener.class controls/CircleListener.class controls/ClearButtonListener.class controls/ClipListener.class controls/DrawingPane.class controls/InterpolSchalterListener.class controls/IterButtonListener.class controls/LineListener.class controls/MatrixEditor.class controls/MoveListener.class controls/PanelButton.class controls/PickListener.class controls/PointListener.class controls/PolygonListener.class controls/RectangleListener.class controls/SaveButtonListener.class controls/SplinePane.class controls/StuetzpunktListener.class controls/TextField.class draw2D/App.class draw2D/Applet.class draw2D/Controller.class draw2D/Model.class draw2D/View.class grOb/Baum.class grOb/Bresenham.class grOb/Circle.class grOb/Fractal.class grOb/GraphicObject.class grOb/Interpol.class grOb/Koch.class grOb/LSystem.class grOb/Line.class grOb/Point.class grOb/Polygon.class grOb/Rectangle.class tools/DitherMatrix.class tools/Matrix.class tools/MatrixManager.class tools/MatrixModel.class tools/NxNMatrix.class tools/OrderedDitherMatrix.class tools/Resetable.class tools/Switchable.class tools/WhiteNoiseDither.class

all:
	make $(TARGETS)

jar:	$(TARGETS)
	-(cd .. && jar cvmf cg/MainClass.txt cg.jar cg draw2D.html)

doc:	
	rm -rf ../Doc
	mkdir ../Doc
	javadoc -d ../Doc -author -version -link http://java.sun.com/j2se/1.4/docs/api cg.draw2D cg.grOb cg.controls cg.tools

ex:	$(TARGETS)
	java cg.draw2D.App

new:
	make clean
	make all

%.class:	%.java
		javac $*.java


clean:
	rm -f draw2D/*.class grOb/*.class controls/*.class tools/*.class core

cleanest:
	make clean
	rm -rf ../cg.jar
	rm -rf ../Doc
