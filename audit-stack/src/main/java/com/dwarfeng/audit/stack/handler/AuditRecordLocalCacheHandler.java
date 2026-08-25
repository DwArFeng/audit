package com.dwarfeng.audit.stack.handler;

import com.dwarfeng.audit.stack.struct.AuditRecordLocalCache;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 审计记录本地缓存处理器。
 *
 * <p>
 * 该处理器实现了 <code>LocalCacheHandler&lt;StringIdKey, AuditRecordLocalCache&gt;</code> 接口，
 * 用于处理与审计记录相关的本地缓存。<br>
 * 对于 <code>LocalCacheHandler&lt;K, V&gt;</code> 接口中的泛型参数:
 * <table>
 *     <tr>
 *         <th></th>
 *         <th>类型</th>
 *         <th>含义</th>
 *     </tr>
 *     <tr>
 *         <td>K</td>
 *         <td>StringIdKey</td>
 *         <td>审计类别主键</td>
 *     </tr>
 *     <tr>
 *         <td>V</td>
 *         <td>AuditRecordLocalCache</td>
 *         <td>审计记录本地缓存</td>
 *     </tr>
 * </table>
 *
 * <p>
 * 该处理器的实现应该是线程安全的。
 *
 * @author DwArFeng
 * @see LocalCacheHandler
 * @see AuditRecordLocalCache
 * @since 1.0.0-beta
 */
public interface AuditRecordLocalCacheHandler extends LocalCacheHandler<StringIdKey, AuditRecordLocalCache> {
}
