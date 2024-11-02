package com.tboostai_llm.common;

public class GeneralConstants {

    public static final int TIMEOUT = 100000;
    public static final String OPENAI_SYSTEM = "system";
    public static final String OPENAI_USER = "user";
    public static final String APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String ACCEPT = "Accept";
    public static final String AUTHORIZATION = "Authorization";
    public static final String OPENAI_MODEL_KEY = "model";
    public static final String OPENAI_MODEL_VALUE = "gpt-4o";
    public static final String OPENAI_ROLE = "role";
    public static final String OPENAI_CONTENT = "content";
    public static final String OPENAI_MESSAGES = "messages";
    public static final String OPENAI_MESSAGE = "message";
    public static final String OPENAI_CHOICES = "choices";
    public static final String OPENAI_SYSTEM_DEFAULT_MSG_FOR_BEAUTIFUL_DESC =
            """
                               You are a professional writer with a strong ability to extract and summarize key information.\s
                               Extract the Vehicle's description from the given content and summarize it as concise bullet points,\s
                               with each point limited to around 20-30 characters to avoid excessive detail. Remove repeated details in bullet points,\s
                               like the same engine type mentioned multiple times. For extractedFeatures, focus on general configurations and common options,\s
                               and avoid duplicate or overly specific details tied to a single model. Respond in JSON format like this:\s
                               {\s
                               "originalDescription": "This is the description of the item",\s
                               "summarized": ["description bullet point 1", "description bullet point 2"],\s
                               "extractedFeatures": ["sunroof", "GPS", "Apple CarPlay"] }.\s
                               Ensure JSON is in raw format without additional formatting.
                   \s""";
}
