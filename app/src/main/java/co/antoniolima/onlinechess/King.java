package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.BOARD_WIDTH;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_KING;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_KING;

public class King extends Piece {

    private boolean hasMadeFirstMove;

    public King(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_KING);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_KING);
        this.hasMadeFirstMove = false;
        //this.initTargetPositions();
    }

    @Override
    public void initTargetPositions(GameController gameController){
        this.resetTargetPositions();
        int pieceX = this.getPosition()%BOARD_WIDTH;
        int pieceY = this.getPosition()/BOARD_WIDTH;
        this.addTargetPosition(new Position(pieceX, pieceY-1));         // norte
        this.addTargetPosition(new Position(pieceX+1, pieceY-1));   // nordeste
        this.addTargetPosition(new Position(pieceX+1, pieceY));         // este
        this.addTargetPosition(new Position(pieceX+1, pieceY+1));   // sudeste
        this.addTargetPosition(new Position(pieceX, pieceY+1));         // sul
        this.addTargetPosition(new Position(pieceX-1, pieceY+1));   // sudoeste
        this.addTargetPosition(new Position(pieceX-1, pieceY));        // oeste
        this.addTargetPosition(new Position(pieceX-1, pieceY-1));   // nordoeste
    }

    @Override
    public  void calculateTargetPositions(GameController gameController){

        this.resetAvailablePositions();
        this.initTargetPositions(gameController);
        // cycle through targets array and check which positions are within the board
        for (Position p : this.getTargetPositionsArray()) {
            if (gameController.isThisPositionValid(p)){
                // se ta livre, é valida
                if (!gameController.isThisPositionTaken(gameController.getUniCoordinate(p))){
                    p.setValid(true);
                    // se ta ocupada, vê a cor da peça
                } else {
                    // se é diferente, é valida
                    if (gameController.getPieceByPosition(gameController.getUniCoordinate(p)).getColor() != this.getColor()){
                        p.setValid(true);
                    }
                }
            }
        }
    }

    @Override
    public void findAvailablePositions(GameController gameController){
        this.resetAvailablePositions();

        // TODO: check if there are pieces in the way
    }

    @Override
    public void select(GameController gameController) {
        this.initTargetPositions(gameController);
        this.calculateTargetPositions(gameController);
        gameController.resetHighlights();
        // highlight itself
        gameController.highlightPosition(this.getPosition());
        // highlight valid positions
        for (Position p : this.getTargetPositionsArray()) {
            if (p.isValid()){
                gameController.highlightPosition(gameController.getUniCoordinate(p));
            }
        }
    }

    @Override
    public void move(GameController gameController, int p) {

        // verifica se p é uma posicao das validas
        for (Position pos : this.getTargetPositionsArray()) {
            if (gameController.getUniCoordinate(pos) == p){
                gameController.resetHighlights();
                this.setPosition(p);
                hasMadeFirstMove = true;
                // se a peça é movida, deixa de estar selecionada
                gameController.setSelectedPiece(null);
                // passa o turno
                gameController.nextTurn();
            }
        }
    }
}