package view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public String input() {
        String line = Console.readLine();
        validate(line);
        return line;
    }

    private void validate(final String input) {
        noNull(input);
        noBlank(input);
    }

    private void noNull(final String input) {
        if (input == null) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private void noBlank(final String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }
}
