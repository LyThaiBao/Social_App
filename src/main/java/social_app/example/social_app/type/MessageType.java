package social_app.example.social_app.type;

public enum MessageType {
    TEXT,
    NOTIFICATION,// use for notification like: member A just left group (content == null)
    RECALLED // use for recall feature (content == null)
}
