package org.jy.framework.sharde.example.sharding;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;

/**
 * 表分库逻辑函数
 * @author zhanglj
 *
 */
public class UserSingleKeyDatabaseShardingAlgorithm implements
		SingleKeyDatabaseShardingAlgorithm<Integer> {

	@Override
	public Collection<String> doBetweenSharding(Collection<String> availableTargetNames,
			ShardingValue<Integer> shardingValue) {
		Collection<String> result = new LinkedHashSet<String>(availableTargetNames.size());  
        Range<Integer> range = (Range<Integer>) shardingValue.getValueRange();  
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {  
            for (String each : availableTargetNames) {  
                if (each.endsWith(i % 3 + "")) {  
                    result.add(each);  
                }  
            }  
        }  
        return result;  
	}

	@Override
	public String doEqualSharding(Collection<String> availableTargetNames,
			ShardingValue<Integer> shardingValue) {
		for (String each : availableTargetNames) {
			if (each.endsWith(shardingValue.getValue() % 3 + "")){
				return each;
			}
		}
		throw new IllegalArgumentException();
	}

	@Override
	public Collection<String> doInSharding(Collection<String> availableTargetNames,
			ShardingValue<Integer> shardingValue) {
		Collection<String> result = new LinkedHashSet<String>(availableTargetNames.size());  
        for (Integer value : shardingValue.getValues()) {  
            for (String tableName : availableTargetNames) {  
                if (tableName.endsWith(value % 3 + "")) {  
                    result.add(tableName);  
                }  
            }  
        }  
        return result;  
	}

}
