package cn.ml.base.http;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import cn.ml.base.MLBaseEntity;


@Table(name = "HttpCache")
public class MLHttpCacheEntity {
	 
    public MLHttpCacheEntity() {
		super();
	}

	
    public MLHttpCacheEntity(String key, String value,long expiry) {
		super();
		this.key = key;
		this.value = value;
		if(expiry!=0){
			this.expiry =System.currentTimeMillis()/1000+expiry/1000;
		}
	}

    @Id
	@Column(column = "key")
    public String key;

    @Column(column = "value")
    public String value;

    @Column(column = "expiry")
    public long expiry;
}
