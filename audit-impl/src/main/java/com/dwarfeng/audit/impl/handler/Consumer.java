package com.dwarfeng.audit.impl.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 批量消费者。
 *
 * @param <D> 消费数据类型。
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public interface Consumer<D> {

    /**
     * 消费指定的数据。
     *
     * @param datas 指定的数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void consume(List<D> datas) throws HandlerException;
}
