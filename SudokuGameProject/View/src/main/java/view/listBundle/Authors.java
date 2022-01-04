package view.listBundle;

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {


    @Override
    protected Object[][] getContents() {
        Object[][] recources = new Object[2][2];
        recources[0][0] = "author1";
        recources[0][1] = "Paul Dera";

        recources[1][0] = "author2";
        recources[1][1] = "Philip Grzelak";

        return recources;
    }

}
