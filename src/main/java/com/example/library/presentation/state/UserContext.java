package com.example.library.presentation.state;

public class UserContext {
    private UserState state;

    public void setState(UserState state) {
        this.state = state;
    }

    public void execute() {
        if (state != null) {
            state.handle();
        } else {
            System.out.println(" No user state defined.");
        }
    }
}
