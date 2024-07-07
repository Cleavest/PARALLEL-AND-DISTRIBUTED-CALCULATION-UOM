package second;

import java.io.*;
import java.util.*;

public class Reply implements Serializable {

    private String opcode;
    private int num;

    public Reply() {
        opcode = null;
        num = 0;
    }

    public void setOpcode(String op) {
        opcode = op;
    }

    public void setValue(int n) {
        num = n;
    }

    public String getOpcode() {
        return opcode;
    }
    public int getValue() {
        return num;
    }

}
