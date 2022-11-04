package phase1.model;

import java.util.HashMap;
import java.util.Map;

class Automata {

    private Map<State, Token> acceptStates;

    public Automata() {
        acceptStates = new HashMap<>();
        acceptStates.put(State.Q1, Token.IDENTIFIER);
        acceptStates.put(State.Q3, Token.STRING);
        acceptStates.put(State.Q4, Token.INTEGER);
        acceptStates.put(State.Q7, Token.FLOAT);
    }

    private State executeTransition(State currentState, char input) {
        switch (currentState) {
            case INITIAL: {
                if ((input >= 'A' && input <= 'Z') || (input >= 'a' && input <= 'z'))
                    return State.Q1;
                else if (input == '"')
                    return State.Q2;
                else if (input >= '0' && input <= '9')
                    return State.Q4;
                else if (input == '+' || input == '-')
                    return State.Q5;
                else
                    return State.INVALID_TOKEN;
            }

            case Q1: {
                return (input >= 'A' && input <= 'Z')
                        || (input >= 'a' && input <= 'z')
                        || (input >= '0' && input <= '9')
                        ? State.Q1 : State.INVALID_TOKEN;
            }

            case Q2: {
                return (input == '"') ? State.Q3 : State.Q2;
            }

            case Q4: {
                if (input == '.')
                    return State.Q6;
                else if (input >= '0' && input <= '9')
                    return State.Q4;
                else
                    return State.INVALID_TOKEN;
            }

            case Q5: {
                return (input >= '0' && input <= '9') ? State.Q4 : State.INVALID_TOKEN;
            }

            case Q6,Q7: {
                return (input >= '0' && input <= '9') ? State.Q7 : State.INVALID_TOKEN;
            }

            default:
                return State.INVALID_TOKEN;
        }
    }

    public Token evaluate(String str){
        State state = State.INITIAL;
        for(char c : str.toCharArray()){
            state = executeTransition(state, c);
        }
        return acceptStates.getOrDefault(state, Token.INVALID);
    }
}

