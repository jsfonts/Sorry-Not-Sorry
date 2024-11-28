MODELS_SRC = $(wildcard src/models/*.java)
MODELS = $(MODELS_SRC:src/models/%.java = models/%.class)

CONTROLLERS_SRC = $(wildcard src/controllers/*.java)
CONTROLLERS = $(CONTROLLERS_SRC:src/controllers/%.java = controllers/%.class)

VIEWS_SRC = $(wildcard src/views/*.java)
VIEWS = $(VIEWS_SRC:src/views/%.java=views/%.class)


all: views controllers models
controllers: $(CONTROLLERS)
models: $(MODELS)
views: $(VIEWS)

#Controller Dependencies

controllers/%.class:
	javac -d bin -cp bin src/controllers/$*.java

controllers/GameController.class : models/Player.class models/Deck.class views/GameBoard.class

#View Dependencies

views/%.class:
	javac -d bin -cp bin src/views/$*.java

views/GameBoard.class: views/MainMenu.class

views/MainMenu.class: controllers/GameController.class

#Model Dependencies 

models/%.class: src/models/%.java
	javac -d bin -cp bin src/models/$*.java

models/HumanPlayer.class: models/Player.class

models/ComputerPlayer.class: models/Player.class

models/Player.class: models/Pawn.class 

models/Deck.class: models/Card.class

clean:
	rm -r bin/*
