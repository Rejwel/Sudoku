package view.listbundle;

import java.util.ListResourceBundle;

public class Authors_pl extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] recources = new Object[2][2];
        recources[0][0] = "author1";
        recources[0][1] = "Paweł Dera";

        recources[1][0] = "author2";
        recources[1][1] = "Filip Grzelak";

        return recources;
    }
}
