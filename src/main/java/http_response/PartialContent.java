package http_response;

public class PartialContent {

    private final String content;
    private final Integer lowerBound;
    private final Integer upperBound;

    public PartialContent(String content, Integer lowerBound, Integer upperBound) {
        this.content = content;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public String body() {
        return content.substring(getStartIndex(), getEndIndex());
    }

    private int getStartIndex() {
        if(lowerBound == null && upperBound == null) {
            return 0;
        } else if (lowerBound == null) {
            return content.length() - upperBound;
        } else {
            return this.lowerBound;
        }
    }

    private int getEndIndex() {
        if(upperBound == null || lowerBound == null) {
            return content.length();
        } else {
            return upperBound + 1;
        }
    }

}
