package lana.sockserver.util.hashing;

public interface HashingUtil {
    String hash(String input);

    String hash(String input, String salt);

    String random();
}
