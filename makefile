MODELS_SRC = $(wildcard src/models/*.java)
MODELS = $(MODELS_SRC:src/models/%.java=models/%.class)

CONTROLLERS_SRC = $(wildcard src/controllers/*.java)
CONTROLLERS = $(CONTROLLERS_SRC:src/controllers/%.java=controllers/%.class)

VIEWS_SRC = $(wildcard src/views/*.java)
VIEWS = $(VIEWS_SRC:src/views/%.java=views/%.class)


#all: models views controllers
all:
	javac -d bin -cp bin src/models/*.java src/controllers/*.java src/views/*.java
	javac -d bin -cp bin src/Main.java

controllers: $(CONTROLLERS)
models: $(MODELS)
views: $(VIEWS)

#Controller Dependencies

controllers/%.class:
	javac -d bin -cp bin src/controllers/$*.java

controllers/GameController.class: models/Deck.class models/Board.class models/HumanPlayer.class views/GameView.class views/MainMenu.class src/controllers/GameController.java

#View Dependencies

views/%.class:
	javac -d bin -cp bin src/views/$*.java

views/MainMenu.class: controllers/GameController.class

views/GameView.class: src/views/GameView.java controllers/GameController.class

views/CardView.class: models/Deck.class

#Model Dependencies 

models/%.class: src/models/%.java
	javac -d bin -cp bin src/models/$*.java

models/HumanPlayer.class: models/Player.class

models/ComputerPlayer.class: models/Player.class

models/Player.class: models/Pawn.class 

models/Deck.class: models/Card.class

models/Board.class: models/Tile.class models/Player.class

clean:
	rm -r bin/*
