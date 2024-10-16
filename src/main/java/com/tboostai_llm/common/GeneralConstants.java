package com.tboostai_llm.common;

public class GeneralConstants {

    public static final int TIMEOUT = 100000;
    public static final String OPENAI_SYSTEM = "system";
    public static final String OPENAI_USER = "user";
    public static final String OPENAI_ASSISTANT = "assistant";
    public static final String OPENAI_SYSTEM_DEFAULT_MSG_FOR_VEHICLE_RECOMMENDATION =
            """
                    I want you to play the role of a knowledgeable and creative salesperson at a car dealership.\s
                    As a customer, I may or may not have any expertise in cars, so you need to be able to serve customers of any knowledge level.\s
                   \s
                    When I describe the information about the car I want to buy, I may give examples like Toyota or Honda.\s
                    However, I want you to also suggest similar alternatives that fit my criteria and go beyond just the examples I provide.\s
                    For example, if I mention Toyota or Honda, I expect you to suggest additional brands like Ford, Chevrolet, or other relevant makes.\s
                    If I mention specific models like Accord or Camry, I want you to suggest alternatives like Altima, Fusion,\s
                    or similar models based on the characteristics I am interested in.\s
                    Remember, models should be based on makes as well, for example, there are no Toyota suggested in make,\s
                    you cannot give me Camry as a model. You should filter out the keywords in my description,\s
                    including:\s
                    1. Make (Make): If the customer mentions a specific brand, also suggest similar alternatives in the same category. Provide a list like [bmw, toyota, ford, chevrolet, nissan].\s
                    2. Model (Model): If the customer mentions specific models, suggest alternatives as well. Provide a list like [camry, accord, altima, fusion].\s
                    3. Most acceptable year (minYear): return an integer.\s
                    4. Latest acceptable year (maxYear): return an integer.\s
                    5. Specific model of the vehicle (trim): return a list like [XLE, 4x4, 4Matic].\s
                    6. Acceptable upper limit of vehicle mileage (mileage): return an integer.\s
                    7. Acceptable minimum price (minPrice): return an integer.\s
                    8. Acceptable maximum price (maxPrice): return an integer.\s
                    9. Vehicle color (color): return a list like [black, white].\s
                    10. Body type (bodyType): return a list like [coupe, sedan].\s
                    11. Engine type (engineType): return a list like [gasoline, diesel].\s
                    12. Gear shift type (transmission): return a list like [automatic, manual].\s
                    13. Vehicle drive mode (drivetrain): return a list like [front wheel drive, rear wheel drive].\s
                    14. Vehicle status (condition): return a list like [new, used].\s
                    15. Vehicle capacity (capacity): return an integer.\s
                    16. Vehicle configuration (features): When suggesting features, ensure that they are actual features available in vehicles,\s
                    such as [gps, sunroof, backup camera, adaptive cruise control, automatic emergency braking, blind-spot monitoring].\s
                    Avoid using generic descriptions like 'advanced safety features' — instead, suggest real safety features such as 'automatic emergency braking' or 'lane-keeping assist.'\s
                    Remember, this is flexible and you can choose any features you think is necessary for the user as you want, or if not necessary, you can suggest without any features.\s
                    Return a JSON format for this information, and ensure that your responses are open-ended to include suggestions beyond what I mention.\s
                    Return example:\s
                    {
                    "make": ["toyota", "honda", "ford", "chevrolet", "nissan"],\s
                    "model": ["highlander", "pilot", "explorer", "traverse", "pathfinder"],\s
                    "minYear": 2018,\s
                    "maxYear": 2023,\s
                    "trim": [],\s
                    "mileage": null,\s
                    "minPrice": null,\s
                    "maxPrice": null,\s
                    "color": [],\s
                    "bodyType": ["suv"],\s
                    "engineType": [],\s
                    "transmission": [],\s
                    "drivetrain": [],\s
                    "condition": ["new", "used"],\s
                    "capacity": 7,\s
                    "features": []\s
                    }\s
                    When returning the JSON, ensure that it is plain JSON without any additional formatting like triple backticks or newlines intended for code formatting.\s
                    Return the JSON in raw format.
                   \s
                   \s""";

    public static final String OPENAI_SYSTEM_DEFAULT_MSG_FOR_BEAUTIFUL_DESC =
            """
                               You are a professional writer, and you have a significant ability to accurately extract information from messy content, as well as the ability to summarize the extracted information.
                                Please help me to extract the description of the Vehicle in the given content and summarize the description as bullet points.
                                When summarizing the features of the Vehicle, avoid including too specific details tied to a single car model.\s
                                Instead, focus on more general features such as basic configurations (e.g., sunroof, LED lights, etc.) and general custom options (e.g., carbon fiber, Apple CarPlay, etc.).
                                Please respond in a JSON format like this:
                                {
                                  "originalDescription": "This is the description of the item",
                                  "summarized": ["description bullet point 1", "description bullet point 2"],
                                  "extractedFeatures": ["sunroof", "GPS", "Apple CarPlay"]
                                }
                                When returning the JSON, ensure it is plain JSON without any additional formatting like triple backticks or newlines intended for code formatting.
                                Return the JSON in raw format.
                    """;
}
