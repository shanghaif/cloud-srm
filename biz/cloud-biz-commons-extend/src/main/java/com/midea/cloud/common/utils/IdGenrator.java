package com.midea.cloud.common.utils;

/**
 * <pre>
 *  ID生成器
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-12 12:17
 *  修改内容:
 * </pre>
 */
public class IdGenrator {

    /**
     * 起始的时间戳
     */
    private static final long START_STMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     */
    private static final long SEQUENCE_BIT = 7; //序列号占用的位数
    private static final long MACHINE_BIT = 3;   //机器标识占用的位数
    private static final long DATACENTER_BIT = 1; //数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private static final long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private static final long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private static final long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳


    /**
     * @param datacenterId the datacenterId to set
     */
    public void setDatacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
    }

    /**
     * @return the datacenterId
     */
    public long getDatacenterId() {
        return datacenterId;
    }

    /**
     * @param machineId the machineId to set
     */
    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    public long getMachineId() {
        return machineId;
    }

    public IdGenrator(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    private static final long DEFAULT_DATA_CENTER_ID = System.getProperty("gen_data_center_id") != null ? Long.valueOf(System.getProperty("gen_data_center_id")) : 0;
    private static final long DEFAULT_MACHINE_ID = System.getProperty("gen_machine_id") != null ? Long.valueOf(System.getProperty("gen_machine_id")) > 7 ? 3 : Long.valueOf(System.getProperty("gen_machine_id")) : 0;

    private static IdGenrator idGenerator;

    /**
     * ID生成
     *
     * @return
     */
    public static long generate() {
        if (null == idGenerator) {
            idGenerator = new IdGenrator(DEFAULT_DATA_CENTER_ID, DEFAULT_MACHINE_ID);
        }
        return idGenerator.nextId();
    }

}