/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>
 *
 */
package de.nomagic.printerController.pacemaker;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nomagic.printerController.Tool;
import de.nomagic.printerController.planner.AxisConfiguration;
import de.nomagic.printerController.printer.Cfg;

/**
 * @author Lars P&ouml;tter
 * (<a href=mailto:Lars_Poetter@gmx.de>Lars_Poetter@gmx.de</a>)
 */
public class Protocol
{
    // Magic Number from Protocol Definition:
// Host
    public final static int START_OF_HOST_FRAME = 0x23;

    public final static byte ORDER_RESET = (byte)0x7f;
    public final static byte ORDER_RESUME = 0;
    public final static byte QUERY_STOPPED_STATE = 0;
    public final static byte ACKNOWLEADGE_STOPPED_STATE = 1;
    public final static byte CLEAR_STOPPED_STATE = 2;

    public final static byte ORDER_REQ_INFORMATION = 1;

    public final static int INFO_FIRMWARE_NAME_STRING = 0;
    public final static int INFO_SERIAL_NUMBER_STRING = 1;
    public final static int INFO_BOARD_NAME_STRING = 2;
    public final static int INFO_GIVEN_NAME_STRING = 3;
    public final static int INFO_SUPPORTED_PROTOCOL_VERSION_MAJOR = 4;
    public final static int INFO_SUPPORTED_PROTOCOL_VERSION_MINOR = 5;
    public final static int INFO_LIST_OF_SUPPORTED_PROTOCOL_EXTENSIONS = 6;

    public final static int INFO_PROTOCOL_EXTENSION_STEPPER_CONTROL = 0;
    public final static int INFO_PROTOCOL_EXTENSION_QUEUED_COMMAND = 1;
    public final static int INFO_PROTOCOL_EXTENSION_BASIC_MOVE = 2;
    public final static int INFO_PROTOCOL_EXTENSION_EVENT_REPORTING = 3;

    public final static int INFO_FIRMWARE_TYPE = 7;
    public final static int INFO_FIRMWARE_REVISION_MAJOR = 8;
    public final static int INFO_FIRMWARE_REVISION_MINOR = 9;
    public final static int INFO_HARDWARE_TYPE = 10;
    public final static int INFO_HARDWARE_REVISION = 11;
    public final static int INFO_NUMBER_STEPPERS = 12;
    public final static int INFO_NUMBER_HEATERS = 13;
    public final static int INFO_NUMBER_PWM = 14;
    public final static int INFO_NUMBER_TEMP_SENSOR = 15;
    public final static int INFO_NUMBER_INPUT = 16;
    public final static int INFO_NUMBER_OUTPUT = 17;
    public final static int INFO_NUMBER_BUZZER = 18;

    public final static byte ORDER_REQ_DEVICE_NAME = 2;
    public final static byte ORDER_REQ_TEMPERATURE = 3;
    public final static byte ORDER_GET_HEATER_CONFIGURATION = 4;
    public final static byte ORDER_CONFIGURE_HEATER = 5;
    public final static byte ORDER_SET_HEATER_TARGET_TEMPERATURE = 6;
    public final static byte ORDER_REQ_INPUT = 7;
    public final static byte INPUT_HIGH = 1;
    public final static byte INPUT_LOW = 0;
    public final static byte ORDER_SET_OUTPUT = 8;
    public final static byte ORDER_SET_PWM = 9;
    public final static byte ORDER_WRITE_FIRMWARE_CONFIGURATION = 0x0A;
    public final static byte ORDER_READ_FIRMWARE_CONFIGURATION = 0x0B;
    public final static byte ORDER_STOP_PRINT = 0x0C;
    public final static byte ORDERED_STOP = 0;
    public final static byte EMERGENCY_STOP = 1;

    // Extension Stepper Control
    public final static byte ORDER_ACTIVATE_STEPPER_CONTROL = 0x0D;
    public final static byte ORDER_ENABLE_DISABLE_STEPPER_MOTORS = 0x0E;
    public final static byte ORDER_CONFIGURE_END_STOPS = 0x0F;
    public final static byte ORDER_ENABLE_DISABLE_END_STOPS = 0x10;
    public final static byte ORDER_HOME_AXES = 0x11;
    public final static int DIRECTION_INCREASING = 1;
    public final static int DIRECTION_DECREASING = 0;

    // Extension Queue Command
    public final static byte ORDER_QUEUE_COMMAND_BLOCKS = 0x12;
    public final static byte MOVEMENT_BLOCK_TYPE_COMMAND_WRAPPER = 0x01;
    public final static byte MOVEMENT_BLOCK_TYPE_DELAY = 0x02;
    public final static byte MOVEMENT_BLOCK_TYPE_SET_ACTIVE_TOOLHEAD = 0x03;

    // Extension: basic move
    public final static byte ORDER_CONFIGURE_AXIS_MOVEMENT_RATES = 0x13;

    // Extension: Event Reporting
    public final static byte ORDER_RETRIEVE_EVENTS = 0x14;
    public final static byte ORDER_GET_NUMBER_EVENT_FORMAT_IDS = 0x15;
    public final static byte ORDER_GET_EVENT_STRING_FORMAT_ID = 0x16;

    public final static byte ORDER_MAX_ORDER = 0x16;

// Client
    public final static int START_OF_CLIENT_FRAME = 0x42;

    public final static byte RESPONSE_FRAME_RECEIPT_ERROR = 0;
    public final static int RESPONSE_BAD_FRAME = 0;
    public final static int RESPONSE_BAD_ERROR_CHECK_CODE = 1;
    public final static int RESPONSE_UNABLE_TO_ACCEPT_FRAME = 2;

    public final static byte RESPONSE_OK = 0x10;
    public final static byte RESPONSE_GENERIC_APPLICATION_ERROR = 0x11;

    public final static int RESPONSE_UNKNOWN_ORDER = 0;
    public final static int RESPONSE_BAD_PARAMETER_FORMAT = 1;
    public final static int RESPONSE_BAD_PARAMETER_VALUE = 2;
    public final static int RESPONSE_INVALID_DEVICE_TYPE = 3;
    public final static int RESPONSE_INVALID_DEVICE_NUMBER = 4;
    public final static int RESPONSE_INCORRECT_MODE = 5;
    public final static int RESPONSE_BUSY = 6;
    public final static int RESPONSE_FAILED = 7;

    public final static byte RESPONSE_STOPPED = 0x12;
    public final static byte STOPPED_UNACKNOWLEADGED = 0;
    public final static byte STOPPED_ACKNOWLEADGED = 1;
    public final static byte RECOVERY_CLEARED = 1;
    public final static byte RECOVERY_PERSISTS = 2;
    public final static byte RECOVERY_UNRECOVERABLE = 3;
    public final static byte CAUSE_RESET = 0;
    public final static byte CAUSE_END_STOP_HIT = 1;
    public final static byte CAUSE_MOVEMENT_ERROR = 2;
    public final static byte CAUSE_TEMPERATURE_ERROR = 3;
    public final static byte CAUSE_DEVICE_FAULT = 4;
    public final static byte CAUSE_ELECTRICAL_FAULT = 5;
    public final static byte CAUSE_FIRMWARE_FAULT = 6;
    public final static byte CAUSE_OTHER_FAULT = 7;

    public final static byte RESPONSE_ORDER_SPECIFIC_ERROR = 0x13;

    public final static int RESPONSE_MAX = 0x13;

    public final static byte DEVICE_TYPE_UNUSED = 0;
    public final static byte DEVICE_TYPE_INPUT = 1;
    public final static byte DEVICE_TYPE_OUTPUT = 2;
    public final static byte DEVICE_TYPE_PWM_OUTPUT = 3;
    public final static byte DEVICE_TYPE_STEPPER = 4;
    public final static byte DEVICE_TYPE_HEATER = 5;
    public final static byte DEVICE_TYPE_TEMPERATURE_SENSOR = 6;
    public final static byte DEVICE_TYPE_BUZZER = 7;

    ////////////////////////////////////////////////////////////////////////////
    // end of Magic Number from Protocol Definition
    ////////////////////////////////////////////////////////////////////////////


    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    // time between two polls in milliseconds.
    private final int POLLING_TIME_MS = 100;
    // resolution of Delay : 10us
    private final double DELAY_TICK_LENGTH = 0.00001;
    // the client needs at lest this(in milliseconds) time to free up one slot in the Queue
    private final long QUEUE_POLL_DELAY = 10;
    // blocking command will try not more than MAX_ENQUEUE_DELAY times to enqueue the block
    private final int MAX_ENQUEUE_DELAY = 100;

    private ClientConnection cc;
    private AxisConfiguration[] axisCfg;
    private Cfg cfg;
    private DeviceInformation di = new DeviceInformation();

    private final static int QUEUE_SEND_BUFFER_SIZE = 200;
    public int ClientQueueFreeSlots = 0;
    public int ClientExecutedJobs = 0;
    public int CommandsSendToClient = 0;

    public Protocol()
    {
    }

    public boolean ConnectToChannel(final ClientConnection cc)
    {
        this.cc = cc;
        // take client out of Stopped Mode
        return sendOrderExpectOK(ORDER_RESUME, CLEAR_STOPPED_STATE);
    }

    public void setCfg(final Cfg cfg)
    {
        this.cfg = cfg;
        axisCfg = cfg.getAxisMapping();
    }

    public Reply sendInformationRequest(final int which) throws IOException
    {
        final byte[] request = new byte[1];
        request[0] = (byte)(0xff & which);
        return cc.sendRequest(ORDER_REQ_INFORMATION, request);
    }

    public Reply sendDeviceNameRequest(final byte type, final int index) throws IOException
    {
        final byte[] request = new byte[2];
        request[0] = type;
        request[1] = (byte)(0xff & index);
        return cc.sendRequest(ORDER_REQ_DEVICE_NAME, request);
    }

    public DeviceInformation getDeviceInformation()
    {
        final DeviceInformation paceMaker = new DeviceInformation();
        try
        {
            if(true == paceMaker.readDeviceInformationFrom(this))
            {
                log.info(paceMaker.toString());
                di = paceMaker;
                return paceMaker;
            }
            else
            {
                return null;
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean sendOrderExpectOK(final byte order, final byte parameter)
    {
        byte[] help = new byte[1];
        help[0] = parameter;
        return sendOrderExpectOK(order, help);
    }

    public boolean sendOrderExpectOK(final byte order, final byte[] parameter)
    {
        final Reply r = cc.sendRequest(order, parameter);
        if(null == r)
        {
            return false;
        }
        return r.isOKReply();
    }

    public int sendOrderExpectInt(final byte order, final byte[] parameter)
    {
        final Reply r = cc.sendRequest(order, parameter);
        if(null == r)
        {
            return -1;
        }
        if(false  != r.isOKReply())
        {
            return -1;
        }
        final byte[] reply = r.getParameter();
        switch(reply.length)
        {
        case 1: return reply[0]; // 8 bit int
        case 2: return reply[0]* 256 + reply[1]; // 16 bit int
        default: return -1;
        }
    }

    public boolean isEndSwitchTriggered(final int axis, final int direction)
    {
        int SwitchNum = -1;
        boolean inverted = true;

        if(DIRECTION_INCREASING == direction)
        {
            SwitchNum = axisCfg[axis].getMaxSwitch();
            inverted = axisCfg[axis].getMaxSwitchInverted();
        }
        else
        {
            SwitchNum = axisCfg[axis].getMinSwitch();
            inverted = axisCfg[axis].getMinSwitchInverted();
        }
        if(Cfg.INVALID == SwitchNum)
        {
            log.error("Can not read status of an Invalid switch !");
            return false;
        }
        final byte[] param = new byte[2];
        param[0] = DEVICE_TYPE_INPUT;
        param[1] = (byte) SwitchNum;
        final int reply = sendOrderExpectInt(Protocol.ORDER_REQ_INPUT, param);
        if(false == inverted)
        {
            if(INPUT_HIGH == reply)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            if(INPUT_LOW == reply)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public void waitForEndSwitchTriggered(final int axis, final int direction)
    {
        log.info("Waiting for homing of axis {} !", axis);
        boolean isTriggered = isEndSwitchTriggered(axis, direction);
        while(false == isTriggered)
        {
            try
            {
                Thread.sleep(POLLING_TIME_MS);
            }
            catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
            isTriggered = isEndSwitchTriggered(axis, direction);
        }
    }

    public boolean setTemperature(final int heaterNum, final Double temperature)
    {
        /* TODO
        temperature = temperature * 10;
        final int tempi = temperature.intValue();
        final byte[] param = new byte[4];
        param[0] = (byte)heaters[heaterNum];
        param[1] = (byte)(0xff & (tempi/256));
        param[2] = (byte)(tempi & 0xff);
        param[3] = (byte)temperatureSensors[heaterNum];
        return sendOrderExpectOK(Protocol.ORDER_SET_HEATER_TARGET_TEMPERATURE, param);
        */
        return false;
    }

    public void waitForHeaterInLimits(final int heaterNumber,
                                      final Double temperature_min,
                                      final Double temperature_max)
    {
        /*
        if(Cfg.INVALID == heaterNumber)
        {
            log.warn("Ignoring waiting for reaching the temperature on a missing Heater !");
            return;
        }
        if(0.0 < temperature)
        {
            log.info("waiting for Temperature to reach {} on heater {}", temperature, heaterNumber);
            if(Cfg.INVALID == temperatureSensors[heaterNumber])
            {
                log.error("No Temperature Sensor available for Heater {} !", heaterNumber);
                return;
            }
            int curTemp = readTemperatureFrom(temperatureSensors[heaterNumber]);
            while(   (curTemp < (temperature - ACCEPTED_TEMPERATURE_DEVIATION))
                  || (curTemp > (temperature + ACCEPTED_TEMPERATURE_DEVIATION)) )
            {
                try
                {
                    Thread.sleep(POLLING_TIME_MS);
                }
                catch (final InterruptedException e)
                {
                    e.printStackTrace();
                }
                curTemp = readTemperatureFrom(temperatureSensors[heaterNumber]);
                log.info("cur Temperature is {}", curTemp);
            }
        }
        // else heater is not active -> already in limit
        return;
        */
    }

    /** sets the speed of the Fan ( Fan 0 = Fan that cools the printed part).
     *
     * @param fan specifies the effected fan.
     * @param speed 0 = off; 255 = max
     */
    public boolean setFanSpeedfor(final int fan, final int speed)
    {
        /* TODO
        if((-1 < fan) && (fan < printerAbilities.getNumberPwmFan()))
        {
            final byte[] param = new byte[3];
            param[0] = (byte) fan;
            param[1] = (byte) speed;
            param[2] = 0;
            if(false == sendOrderExpectOK(Protocol.ORDER_SET_FAN_PWM, param))
            {
                log.error("Falied to enable the Steppers !");
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            log.warn("Client does not have the Fan {} !", fan);
            return true;
        }
        */
        return false;
    }

    /** only needed to implement M17
     *
     * @return
     */
    public boolean enableAllStepperMotors()
    {
        if(true == cfg.shouldUseSteppers())
        {
            final int numSteppers = di.getNumberSteppers();
            byte[] parameter = new byte[2];
            parameter[1] = 0x01; // Enabled
            for(int i = 0; i < numSteppers; i++)
            {
                parameter[0] = (byte)i;
                if(false == sendOrderExpectOK(Protocol.ORDER_ENABLE_DISABLE_STEPPER_MOTORS, parameter))
                {
                    log.error("Falied to enable the Steppers !");
                    return false;
                }
            }
            return true;
        }
        else
        {
            // TODO alternative to Stepper Control Extension
            log.error("Found Enable Stepper Command but Client is not allowed to use the Steppers !");
            return false;
        }

    }

    public boolean disableAllStepperMotors()
    {
        if(true == cfg.shouldUseSteppers())
        {
            if(false == sendOrderExpectOK((byte)Protocol.ORDER_ENABLE_DISABLE_STEPPER_MOTORS, null))
            {
                log.error("Falied to disable the Steppers !");
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            // TODO alternative to Stepper Control Extension
            log.error("Found disable Stepper Command but Client is not allowed to use the Steppers !");
            return false;
        }
    }

    public void startHomeOnAxes(final Vector<Integer> listOfHomeAxes)
    {
        if(null == listOfHomeAxes)
        {
            return;
        }
        final byte[] param = new byte[6 * listOfHomeAxes.size()];
        for(int i = 0; i < listOfHomeAxes.size(); i++)
        {
            final int axis = listOfHomeAxes.get(i);
            if(Cfg.INVALID == axis)
            {
                log.error("Tried to home an invalid axis !");
                return;
            }
            final byte stepNum = (byte)axisCfg[axis].getStepperNumber();
            if(Cfg.INVALID == stepNum)
            {
                log.error("Tried to home an axis with invalid stepper motor! ");
                return;
            }
            param[(i*6) + 0] = stepNum;
            // TODO
        }

        if(false == sendOrderExpectOK(Protocol.ORDER_HOME_AXES, param))
        {
            log.error("Falied to Home the Axis !");
        }
    }

    public boolean doEmergencyStopPrint()
    {
        final byte[] param = new byte[1];
        param[0] = EMERGENCY_STOP;
        return sendOrderExpectOK(Protocol.ORDER_STOP_PRINT, param);
    }

    public boolean addPauseToQueue(final Double seconds)
    {
        if(true == di.hasExtensionQueuedCommand())
        {
            final Double ticks = seconds / DELAY_TICK_LENGTH;
            final int tick = ticks.intValue();
            final byte[] param = new byte[3];
            param[0] = MOVEMENT_BLOCK_TYPE_DELAY;
            param[1] = (byte)(0xff & (tick/256));
            param[2] = (byte)(tick & 0xff);
            return enqueueCommandBlocking(param);
        }
        else
        {
            // no Queue - no chance to add to it.
            return false;
        }
    }


    Vector<byte[]> sendQueue = new Vector<byte[]>();


    /** Enqueues the data for _one_ command into the Queue.
     *
     * CAUTION: All data that can not be send out stays in Memory. So if this
     * function fails repeatedly the memory consumption increases with every
     * call. If in this situation try calling enqueueCommandBlocking() !
     *
     * @param param Data of only one command !
     * @return true = success; false= command could not be put in the queue but
     * will be send with the next command that shall be enqueued.
     */
    private boolean enqueueCommand(byte[] param)
    {
        if(false == sendQueue.isEmpty())
        {
            // add the new command, and...
            sendQueue.add(param);
            // try to get the Queue empty again.
            if(0 == ClientQueueFreeSlots)
            {
                // send only the first command from the command queue
                byte[] firstCommand = sendQueue.get(0);
                final Reply r = cc.sendRequest(ORDER_QUEUE_COMMAND_BLOCKS, firstCommand);
                if(null == r)
                {
                    return false;
                }
                if(true == r.isOKReply())
                {
                    byte[] reply = r.getParameter();
                    ClientQueueFreeSlots = (reply[0] * 256) + reply[1];
                    ClientExecutedJobs = (reply[2] * 256) + reply[3];
                    CommandsSendToClient ++;
                    sendQueue.remove(0);
                    return true;
                }
                else
                {
                    // error -> try again later
                    return false;
                }
            }
            else
            {
                byte[] sendBuffer = new byte[QUEUE_SEND_BUFFER_SIZE];
                int writePos = 0;
                int idx = 0;
                int numBlocksInBuffer = 0;
                for(int i = 0; i < ClientQueueFreeSlots; i++)
                {
                    // add a block to the send buffer until
                    // either send Buffer if full
                    // or all commands have been put in the buffer
                    byte[] buf = sendQueue.get(idx);
                    if(null != buf)
                    {
                        if(buf.length < QUEUE_SEND_BUFFER_SIZE - writePos)
                        {
                            for(int j = 0; j < buf.length; j++)
                            {
                                sendBuffer[writePos + j] = buf[j];
                            }
                            writePos = writePos + buf.length;
                            numBlocksInBuffer ++;
                        }
                        else
                        {
                            break;
                        }
                    }
                    else
                    {
                        break;
                    }
                    idx++;
                }
                // or the loop ends
                // then send them
                final Reply r = cc.sendRequest(ORDER_QUEUE_COMMAND_BLOCKS,
                                               sendBuffer, // data
                                               0, // offset
                                               writePos, // length
                                               false // not cached
                                               );
                // and see what happens.
                if(null == r)
                {
                    return false;
                }
                if(true == r.isOKReply())
                {
                    byte[] reply = r.getParameter();
                    ClientQueueFreeSlots = (reply[0] * 256) + reply[1];
                    ClientExecutedJobs = (reply[2] * 256) + reply[3];
                    CommandsSendToClient = CommandsSendToClient + numBlocksInBuffer;
                    for(int i = 0; i < numBlocksInBuffer; i++)
                    {
                        sendQueue.remove(0);
                    }
                    return true;
                }
                else if(RESPONSE_ORDER_SPECIFIC_ERROR == r.getReplyCode())
                {
                    // Order Specific Error
                    byte[] response = r.getParameter();
                    // partly Queued
                    int numberOfQueued = response[1];
                    for(int i = 0; i < numberOfQueued; i++)
                    {
                        sendQueue.remove(0);
                    }
                    ClientQueueFreeSlots = (response[2] * 256) + response[3];
                    ClientExecutedJobs = (response[4] * 256) + response[5];
                    if(0x01 != response[0])
                    {
                        // Error caused by bad Data !
                        log.error("Could not Queue Block as Client Reports invalid Data !");
                        log.error("Send Data: {} !", Tool.fromByteBufferToHexString(sendBuffer, writePos));
                        log.error("Received : {} !", Tool.fromByteBufferToHexString(response));
                        log.error("Can not recover !");
                        System.exit(1);
                        // what else can I do ?
                    }
                    // else queue full so try next time
                    return false;
                }
                else
                {
                    // error -> send commands later
                    return false;
                }
            }
        }
        else
        {
            // Fast lane. Just send it out.
            final Reply r = cc.sendRequest(ORDER_QUEUE_COMMAND_BLOCKS, param);
            if(null == r)
            {
                return false;
            }
            if(true == r.isOKReply())
            {
                byte[] reply = r.getParameter();
                ClientQueueFreeSlots = (reply[0] * 256) + reply[1];
                ClientExecutedJobs = (reply[2] * 256) + reply[3];
                CommandsSendToClient ++;
                return true;
            }
            else
            {
                // error -> send command later
                sendQueue.add(param);
                return false;
            }
        }
    }


    /** Enqueues the data for _one_ command into the Queue.
     *
     * If the Queue is full this function waits until a free spot becomes
     * available again.
     *
     * @param param Data of only one command !
     * @return true = success; false= command could not be put in the queue.
     */
    private boolean enqueueCommandBlocking(byte[] param)
    {
        // prepare data
        if(null == param)
        {
            log.error("Tried To enque without data !");
            return false;
        }
        if(1 < param.length)
        {
            log.error("Tried To enque with too few bytes !");
            return false;
        }
        if(false == enqueueCommand(param))
        {
            int delayCounter = 0;
            do
            {
                if((delayCounter < MAX_ENQUEUE_DELAY))
                {
                    try
                    {
                        Thread.sleep(QUEUE_POLL_DELAY);
                    }
                    catch(InterruptedException e)
                    {
                    }
                    delayCounter++;
                }
                else
                {
                    return false;
                }
            }while(false == enqueueCommand(null));
        }
        // else sending succeeded !
        return true;
    }

    public boolean applyConfigurationToClient()
    {
        // use or not use the "Stepper Control Extension"
        if((true == cfg.shouldUseSteppers()) && (true == di.hasExtensionStepperControl()))
        {
            if(false == sendOrderExpectOK(ORDER_ACTIVATE_STEPPER_CONTROL, (byte)0x01))
            {
                log.error("Failed to activate Stepper Control Extension !");
                return false;
            }
        }
        // else no extension- no support for command

        // Configure heater (Heater-Temperature sensor mapping)
        int[] heaters = cfg.getHeaterMapping();
        int[] sensors = cfg.getTemperatureSensorMapping();
        byte[] parameter = new byte[2];
        for(int i = 0; i < Cfg.NUMBER_OF_HEATERS; i++)
        {
            int heaterNum = heaters[i];
            int sensorNum = sensors[i];
            if((-1 < heaterNum) && (-1 < sensorNum))
            {
                parameter[0] = (byte)heaterNum;
                parameter[1] = (byte)sensorNum;
                if(false == sendOrderExpectOK(ORDER_CONFIGURE_HEATER, parameter))
                {
                    log.error("Failed to configure Heater {} to use Temperature sensor {} !", heaterNum, sensorNum);
                    return false;
                }
            }
            // else invalid configuration -> skip
        }

        // send all Firmware configuration Values to the Client
        String[] keys = cfg.getAllFirmwareKeys();
        for(int i = 0; i < keys.length; i++)
        {
            writeFirmwareConfigurationValue(keys[i], cfg.getFirmwareSetting(keys[i]));
        }
        return true;
    }

    private void writeFirmwareConfigurationValue(String name, String value)
    {
        byte[] nameBuf = name.getBytes(Charset.forName("UTF-8"));
        byte[] valueBuf = value.getBytes(Charset.forName("UTF-8"));
        byte[] parameter = new byte[nameBuf.length + valueBuf.length + 1];
        parameter[0] = (byte)nameBuf.length;
        for(int i = 0; i < nameBuf.length; i++)
        {
            parameter[i+1] = nameBuf[i];
        }
        for(int i = 0; i < valueBuf.length; i++)
        {
            parameter[i+nameBuf.length] = valueBuf[i];
        }
        if(false == sendOrderExpectOK(ORDER_WRITE_FIRMWARE_CONFIGURATION, parameter))
        {
            log.error("Failed to write Fimrware Setting {} = {} !", name, value);
        }
    }

}
