package delectable.dto;

public class OrderDetailMenuDTO {

		private int id;
		private int count;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		
		public OrderDetailMenuDTO()
		{
			
		}
		public int getItemCount(int i)
		{
			if( i == id)
				return count;
			else
				return 0;
		}
}
