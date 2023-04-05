package client.core.interfaces;

import client.core.managers.CommandsManager;

/**
 * Contains logic-declaration for listening commands from somewhere.
 */
public interface IListener {
    /**
     * start listening.
     */
    void start();

    /**
     * stop listening
     */
    void stop();

    /**
     * get next line
     * @return
     */
    String nextLine();
    Boolean getWorking();
    CommandsManager getCommandsManager();
}
