MODELS_SRC = $(wildcard src/models/*.java)
MODELS = $(MODELS_SRC:src/models/%.java=models/%.class)
CONTROLLERS_SRC = $(wildcard src/controllers/*.java)
CONTROLLERS = $(CONTROLLERS_SRC:src/controllers/%.java=controllers/%.class)

all: models controllers

controllers: $(CONTROLLERS)

controllers/%.class:
	javac -d bin -cp bin src/controllers/$*.java

models: $(MODELS)

models/%.class: src/models/%.java
	javac -d bin -cp bin src/models/$*.java

models/HumanPlayer.class: models/Player.class

models/ComputerPlayer.class: models/Player.class

models/Player.class: models/Pawn.class 

models/Deck.class: models/Card.class

clean:
	rm -r bin/*
