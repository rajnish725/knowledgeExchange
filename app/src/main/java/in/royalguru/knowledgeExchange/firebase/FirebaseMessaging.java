package in.royalguru.knowledgeExchange.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import in.kalmesh.projectbase.Debug;
import in.royalguru.knowledgeExchange.application.ApplicationDetails;
import in.royalguru.knowledgeExchange.sessiondata.SessionManager;


/**
 * Created by Kalmeshwar on 21 Aug 2018 at 16:26.
 */
public class FirebaseMessaging extends FirebaseMessagingService {
    private final String TAG = FirebaseMessaging.class.getSimpleName();

    @Override
    public void onNewToken(String deviceToken) {
        super.onNewToken(deviceToken);

        if (deviceToken != null) {
            new SessionManager(ApplicationDetails.getInstance().getContext()).saveDeviceToken(deviceToken);
            Debug.printLogError(TAG, "device_token: " + deviceToken);
        }
    }

    /**
     * Called when message is  received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Debug.printLogError(TAG, "dataPayload: " + remoteMessage.getData());
        }
    }
}