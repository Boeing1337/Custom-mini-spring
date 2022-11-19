package AddingAQuestion;

public class Adding {


    for(
    int i = 0;
    i<bukvi.length;i++)

    {
        String[] a = (bukvi[i].split("/"));
        String[] b = new String[a.length - a.length / 2 - 1];
        for (int j = 1, k = 0; j < a.length; j += 2, k++) {
            b[k] = a[j];
        }
        A.add(b);
    }


}
