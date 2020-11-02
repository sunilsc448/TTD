package com.example.ttd;

public class StringReverser {
    public String reverse(String input){
        StringBuilder output = new StringBuilder();

        for (int i = input.length()-1; i >= 0; i--){
            output.append(input.charAt(i));
        }
        return output.toString();
    }
}
