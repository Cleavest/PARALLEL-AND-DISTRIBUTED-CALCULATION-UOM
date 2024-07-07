package second;

/**
 * @author Cleavest on 3/5/2024
 */
public class ServerProtocol {

    public String processRequest(String theInput){

        return doServerComputation(theInput);
    }

    public String doServerComputation(String theInput){
        String op = theInput.split(" ")[0];
        StringBuilder builder = new StringBuilder();
        int a,b;
        switch (op) {
            case "+" :
                builder.append("R ");
                a = Integer.parseInt(theInput.split(" ")[1]);
                b = Integer.parseInt(theInput.split(" ")[2]);
                builder.append(a + b);
                break;
            case "-" :
                builder.append("R ");
                a = Integer.parseInt(theInput.split(" ")[1]);
                b = Integer.parseInt(theInput.split(" ")[2]);
                builder.append(a - b);
                break;
            case "*" :
                builder.append("R ");
                a = Integer.parseInt(theInput.split(" ")[1]);
                b = Integer.parseInt(theInput.split(" ")[2]);
                builder.append(a * b);
                break;
            case "/" :
                builder.append("R ");
                a = Integer.parseInt(theInput.split(" ")[1]);
                b = Integer.parseInt(theInput.split(" ")[2]);
                builder.append(a / b);
                break;
            case "!" :
                builder.append("! 0");
                break;
            default: //error handling...
        }

        return builder.toString();
    }
}
