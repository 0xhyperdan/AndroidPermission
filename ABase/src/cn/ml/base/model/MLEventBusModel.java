package cn.ml.base.model;

public class MLEventBusModel {


		public MLEventBusModel() {

		}
		public MLEventBusModel(int type, Object ... obj) {
			this.type = type;
			this.obj = obj;
		}

		public int type;
		public Object[] obj;


}