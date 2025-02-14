package webTests;

public class SuiteDePruebas 
{

    public static void main(String[] args) 
    {
        try 
        {
            wikitest01.main(args);
            wikitest02.main(args);
            wikitest03.main(args);
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}