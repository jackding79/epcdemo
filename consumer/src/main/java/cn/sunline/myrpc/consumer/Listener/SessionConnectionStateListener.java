package cn.sunline.myrpc.consumer.Listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zk连接状态监控
 */
public class SessionConnectionStateListener implements ConnectionStateListener {
    private static final Logger log = LoggerFactory.getLogger(SessionConnectionStateListener.class);

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        if(newState==ConnectionState.CONNECTED){
            log.info("已连接 {}",client);
        }
        if(newState==ConnectionState.LOST){
            log.info("connection lost");
            try
            {
                for (;;)
                {
                    if (client.getZookeeperClient().blockUntilConnectedOrTimedOut())
                    {
                        log.info("reconnection: success");
                        break;
                    }
                }
                return;
            }
            catch (InterruptedException e)
            {
                log.info("reconnection: failure");
            }
            catch (Exception e)
            {
                log.info("reconnection: failure");
            }

        }
    }
}
