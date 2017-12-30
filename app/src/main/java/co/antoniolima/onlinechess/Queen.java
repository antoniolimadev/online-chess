package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.BOARD_WIDTH;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_QUEEN;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_QUEEN;

public class Queen extends Piece {

    public Queen(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_QUEEN);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_QUEEN);
        //this.initTargetPositions();
    }

    @Override
    public void initTargetPositions(GameController gameController){
        this.resetTargetPositions();
        int pieceX = this.getPosition()%BOARD_WIDTH;
        int pieceY = this.getPosition()/BOARD_WIDTH;
        int x; // aux x
        int y; // aux y

        /////////////////////////////////////////////////////// NORDESTE
        // reset a posicao da peça
        x = pieceX;
        y = pieceY;
        // inicializa a primeira posicao da digonal nordeste a verificar
        x++;
        y--;
        Position p = new Position(x,y);
        while(x < BOARD_WIDTH && y >= 0){
            p.setY(y);
            p.setX(x);
            if (gameController.isThisPositionTaken(gameController.getUniCoordinate(p))){
                if (gameController.getPieceByPosition(gameController.getUniCoordinate(p)).getColor() != this.getColor()){
                    this.addTargetPosition(new Position(x, y));
                    break;
                }
                break;
            }
            this.addTargetPosition(new Position(x, y));
            x++;
            y--;
        }
        //////////////////////////////////////////////////////// SUDESTE
        // reset a posicao da peça
        x = pieceX;
        y = pieceY;
        // inicializa a primeira posicao da digonal sudeste a verificar
        x++;
        y++;
        while(x < BOARD_WIDTH && y < BOARD_WIDTH){
            p.setY(y);
            p.setX(x);
            if (gameController.isThisPositionTaken(gameController.getUniCoordinate(p))){
                if (gameController.getPieceByPosition(gameController.getUniCoordinate(p)).getColor() != this.getColor()){
                    this.addTargetPosition(new Position(x, y));
                    break;
                }
                break;
            }
            this.addTargetPosition(new Position(x, y));
            x++;
            y++;
        }
        /////////////////////////////////////////////////////// SUDOESTE
        // reset a posicao da peça
        x = pieceX;
        y = pieceY;
        // inicializa a primeira posicao da digonal sudoeste a verificar
        x--;
        y++;
        while(x >= 0 && y < BOARD_WIDTH){
            p.setY(y);
            p.setX(x);
            if (gameController.isThisPositionTaken(gameController.getUniCoordinate(p))){
                if (gameController.getPieceByPosition(gameController.getUniCoordinate(p)).getColor() != this.getColor()){
                    this.addTargetPosition(new Position(x, y));
                    break;
                }
                break;
            }
            this.addTargetPosition(new Position(x, y));
            x--;
            y++;
        }
        /////////////////////////////////////////////////////// NOROESTE
        // reset a posicao da peça
        x = pieceX;
        y = pieceY;
        // inicializa a primeira posicao da digonal sudoeste a verificar
        x--;
        y--;
        while(x >= 0 && y >= 0){
            p.setY(y);
            p.setX(x);
            if (gameController.isThisPositionTaken(gameController.getUniCoordinate(p))){
                if (gameController.getPieceByPosition(gameController.getUniCoordinate(p)).getColor() != this.getColor()){
                    this.addTargetPosition(new Position(x, y));
                    break;
                }
                break;
            }
            this.addTargetPosition(new Position(x, y));
            x--;
            y--;
        }
        /////////////////////////////////////////////////////// NORTE
        // reset a posicao da peça
        x = pieceX;
        y = pieceY;
        // inicializa a primeira posicao norte a verificar
        //x++;
        y++;
        while(y < BOARD_WIDTH && y >= 0){
            p.setY(y);
            p.setX(x);
            if (gameController.isThisPositionTaken(gameController.getUniCoordinate(p))){
                if (gameController.getPieceByPosition(gameController.getUniCoordinate(p)).getColor() != this.getColor()){
                    this.addTargetPosition(new Position(x, y));
                    break;
                }
                break;
            }
            this.addTargetPosition(new Position(x, y));
            //x++;
            y++;
        }
        /////////////////////////////////////////////////////// SUL
        // reset a posicao da peça
        x = pieceX;
        y = pieceY;
        // inicializa a primeira posicao norte a verificar
        //x++;
        y--;
        while(y < BOARD_WIDTH && y >= 0){
            p.setY(y);
            p.setX(x);
            if (gameController.isThisPositionTaken(gameController.getUniCoordinate(p))){
                if (gameController.getPieceByPosition(gameController.getUniCoordinate(p)).getColor() != this.getColor()){
                    this.addTargetPosition(new Position(x, y));
                    break;
                }
                break;
            }
            this.addTargetPosition(new Position(x, y));
            //x++;
            y--;
        }
        /////////////////////////////////////////////////////// ESTE
        // reset a posicao da peça
        x = pieceX;
        y = pieceY;
        // inicializa a primeira posicao norte a verificar
        x++;
        //y--;
        while(x < BOARD_WIDTH && x >= 0){
            p.setY(y);
            p.setX(x);
            if (gameController.isThisPositionTaken(gameController.getUniCoordinate(p))){
                if (gameController.getPieceByPosition(gameController.getUniCoordinate(p)).getColor() != this.getColor()){
                    this.addTargetPosition(new Position(x, y));
                    break;
                }
                break;
            }
            this.addTargetPosition(new Position(x, y));
            x++;
            //y--;
        }
        /////////////////////////////////////////////////////// OESTE
        // reset a posicao da peça
        x = pieceX;
        y = pieceY;
        // inicializa a primeira posicao norte a verificar
        x--;
        //y--;
        while(x < BOARD_WIDTH && x >= 0){
            p.setY(y);
            p.setX(x);
            if (gameController.isThisPositionTaken(gameController.getUniCoordinate(p))){
                if (gameController.getPieceByPosition(gameController.getUniCoordinate(p)).getColor() != this.getColor()){
                    this.addTargetPosition(new Position(x, y));
                    break;
                }
                break;
            }
            this.addTargetPosition(new Position(x, y));
            x--;
            //y--;
        }
    }

    @Override
    public void calculateTargetPositions(GameController gameController){
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
                // se a peça é movida, deixa de estar selecionada
                gameController.setSelectedPiece(null);
                // passa o turno
                gameController.nextTurn();
            }
        }
    }
}
