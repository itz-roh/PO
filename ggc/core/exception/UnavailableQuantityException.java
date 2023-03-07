package ggc.core.exception;

/**
 * Exception for missing product quantities.
 */

public class UnavailableQuantityException extends Exception {
    private static final long serialVersionUID = 201409301048L;

    private String _idProduct;
    private int _relation;
    private int _stock;

    public UnavailableQuantityException(String id, int stock){
        _idProduct = id;
        _relation = 1;
        _stock = stock;
    }

    public UnavailableQuantityException(String id, int stock, Exception cause) {
        super(cause);
        _idProduct = id;
        _relation = 1;
        _stock = stock;
    }

    public UnavailableQuantityException(String id, int relation, int stock){
        _idProduct = id;
        _relation = relation;
        _stock = stock;
    }

    public UnavailableQuantityException(String id, int realtion, int stock, Exception cause) {
        super(cause);
        _idProduct = id;
        _relation = realtion;
        _stock = stock;
      }

    public String getProductId(){ return _idProduct; }
    public int getRelation(){{ return _relation; }}
    public int getStock(){ return _stock; }
}
