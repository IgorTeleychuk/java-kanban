package main.service;

import main.http.HTTPTaskManager;
import main.http.KVServer;

import java.io.IOException;

public class Managers {
    public static TaskManager getInMemoryTaskManager(HistoryManager historyManager) {
        return new InMemoryTaskManager(historyManager);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static HTTPTaskManager getDefault(HistoryManager historyManager) {
        return new HTTPTaskManager(historyManager, "http://localhost:" + KVServer.PORT, true);
    }
}