
START = Main

all:
	javac *.java
	java $(START)

clean:
	del *.class