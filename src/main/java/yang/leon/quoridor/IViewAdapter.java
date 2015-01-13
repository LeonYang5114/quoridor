package yang.leon.quoridor;


public interface IViewAdapter {

    public void setModel(AbstractModel abstractModel);

    public void update(AbstractView context);

}
