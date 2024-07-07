package second;

/**
 * @author Cleavest on 26/5/2024
 */
public class ServerProtocol {

    public Reply processRequest(Request theInput){

        return doServerComputation(theInput);
    }

    public Reply doServerComputation(Request theInput){
        Reply reply = new Reply();
        String op = theInput.getOpcode();
        switch (op) {
            case "+" :
                reply.setOpcode("OK");
                reply.setValue(theInput.getFirst() + theInput.getSecond());
                break;
            case "-" :
                reply.setOpcode("OK");
                reply.setValue(theInput.getFirst() - theInput.getSecond());
                break;
            case "*" :
                reply.setOpcode("OK");
                reply.setValue(theInput.getFirst() * theInput.getSecond());
                break;
            case "/" :
                reply.setOpcode("OK");
                reply.setValue(theInput.getFirst() / theInput.getSecond());
                break;
            case "!" :
                reply.setOpcode("ERROR");
                reply.setValue(-9999);
                break;
            default: //error handling...
        }

        return reply;
    }
}
