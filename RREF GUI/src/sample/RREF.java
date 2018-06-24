package sample;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public abstract class RREF {

    public static Pane pane;
    public static boolean showSteps=false;

    public static void dispMat(double[][] mat,String text) {
        int numCols = mat[0].length;
        int numRows = mat.length;
        GridPane rrefGridPane = new GridPane();
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints(80,80,80);
            rrefGridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints(60,60,60);
            rrefGridPane.getRowConstraints().add(rowConst);
        }
        for(int i=0; i<numRows ; i++) {
            for (int j = 0; j < numCols; j++) {
                TextField valueField = new TextField();
                valueField.alignmentProperty().setValue(Pos.CENTER);
                valueField.editableProperty().setValue(false);
                valueField.setText(mat[i][j]+"");//
                rrefGridPane.add(valueField, j, i);
                rrefGridPane.setMargin(valueField, new Insets(1, 10, 1, 10));
            }
        }
        rrefGridPane.setAlignment(Pos.CENTER);
        Label step = new Label(text);
        step.setAlignment(Pos.CENTER_LEFT);
        step.setPadding(new Insets(0,20,0,20));
        step.setPrefWidth(pane.getWidth());
        step.setWrapText(true);
        pane.getChildren().addAll(step,rrefGridPane);
        /* for (double[] ints : mat) {
            for (double anInt : ints) {
                System.out.format("%16.2f",anInt);
            }
            System.out.println("\n");
        }
        System.out.println("\n\n");*/
    }

    public static void rref(double[][] mat) {

        int columnDone = 0;//number of pivots that have been done
        for(int j = 0; j<mat[0].length; j++) {//loop through the columns

            for (int i = columnDone; i < mat.length; i++) {//loop through the rows

                if (mat[i][j] != 0) { //loop through the rows of the column from up to down until a non zero element is found
                    if (mat[i][j] != 1) {// check if that element is 1, otherwise make it 1 by dividing the column by its value
                        String str = "dividing row "+(i+1)+" by "+mat[i][j];
                        System.out.println(str);
                        divRowBy(mat[i], mat[i][j]);
                        if(showSteps)
                        dispMat(mat,str);
                    }
                    if (i != columnDone) {//move the row if it is not at the right place
                        String str = "swapping rows " + (i+1) + " and " + (columnDone+1);
                        System.out.println(str);
                        swapRows(mat[i], mat[columnDone]);
                        if(showSteps)
                        dispMat(mat,str);
                    }

                    subRowFromOthers(mat, columnDone, j);//Eliminate other elements on that column and keep only the pivot one
                    columnDone++;
                    break;

                }
            }


        }


    }

    public static int getRank(double[][] rrefMatrice) {
        int nonZero=0;
        for (double[] doubles : rrefMatrice) {
            for (double aDouble : doubles) {
                if (aDouble != 0) {
                    nonZero++;
                    break;
                }
            }
        }
        return nonZero;
    }

    private static void subRowFromOthers(double[][] mat, int rowNum,int column){//keep only a 1 on mat[rowNum][column] and make other elements of column 0
        for(int i=0; i<mat.length ; i++) {
            if(i!=rowNum && (mat[i][column] != 0)) {
                String str = "subtracting ("+(-(mat[i][column] / mat[rowNum][column]))  +" * row "+(rowNum+1)+") from row "+(i+1);
                System.out.println(str);
                addRowsMult(mat[i], mat[rowNum], -(mat[i][column] / mat[rowNum][column]));
                if(showSteps)
                dispMat(mat,str);
            }
        }


    }

    private static void addRowsMult(double[] R1, double[] R, double m) {// R1 = R1+n*R
        for (int i = 0; i < R1.length; i++) {
            R1[i] += R[i] * m;
        }
    }

    private static void swapRows(double[] doubles, double[] doubles1) {
        double[] tmp = new double[doubles.length];
        for (int i = 0; i <doubles.length; i++) {
            tmp[i] = doubles[i];
            doubles[i] = doubles1[i];
            doubles1[i] = tmp[i];
        }
    }

    private static void divRowBy(double[] doubles, double i) {
        for (int j = 0; j < doubles.length; j++) {
            if(doubles[j]!=0)
                doubles[j]/=i;
        }
    }
}
