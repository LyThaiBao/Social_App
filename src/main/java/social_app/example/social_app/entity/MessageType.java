package social_app.example.social_app.entity;

public enum MessageType {
    TEXT,
    IMAGE,
    VIDEO,
    FILE,
    NOTIFICATION,// use for notification like: member A just left group (content == null)
    RECALLED // use for recall feature (content == null)
}
