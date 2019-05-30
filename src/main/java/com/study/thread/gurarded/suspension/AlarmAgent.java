package com.study.thread.gurarded.suspension;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

/**
 * 负责连接告警服务器，并发送告警信息到告警服务器
 */
public class AlarmAgent {

    //记录AlarmAgent是否连接上告警服务器
    private volatile boolean connectionedToServer = false;

    private final Predicate agentConnected = new Predicate() {
        @Override
        public boolean evaluate() {
            return connectionedToServer;
        }
    };

    private final Blocker blocker = new ConditionVarBlocker();

    private final Timer heartbeatTimer = new Timer(true);

    /**
     * 发送告警信息
     * @param alarmInfo 告警信息
     * @throws Exception
     */
    public void sendAlarm(final AlarmInfo alarmInfo) throws Exception {
        //可能需要等待,直到alarm连接上告警服务器（或者连接中断后重新连接上服务器）
        GuardedAction<Void> guardedAction = new GuardedAction<Void>(agentConnected) {
            @Override
            public Void call() throws Exception {
                doSendAlarm(alarmInfo);
                return null;
            }
        };
    }

    private void doSendAlarm(AlarmInfo alarmInfo) {
        System.out.println("send alarm " + alarmInfo);
        try {
            Thread.sleep(50);
        } catch (Exception ignore) {}
    }

    public void init() {
        Thread connectingThread = new Thread(new ConnectingTask());
        connectingThread.start();
        heartbeatTimer.schedule(new HeartbeatTask(), 60000, 2000);
    }

    public void disconnect() {
        connectionedToServer = false;
    }

    protected void onConnected() {
        try {
            blocker.signalAfter(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    connectionedToServer = true;
                    return true;
                }
            });
        } catch (Exception ignore) {}
    }

    /**
     * 负责与告警服务器建立链接
     */
    private class ConnectingTask implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (Exception ignore) {}
        }
    }

    private class HeartbeatTask extends TimerTask {
        @Override
        public void run() {
            if (!testConnection()) {
                onConnected();
                reconnect();
            }
        }
    }

    private boolean testConnection() {
        return true;
    }

    private void reconnect() {
        ConnectingTask connectingTask = new ConnectingTask();
        //直接在心跳定时器线程中执行
        connectingTask.run();
    }
}
