connect --locator=localhost[41111]
import data --region=/BookMaster --member=server1 --file=../data/bookmaster_data.gfd
import data --region=/Customer --member=server1 --file=../data/customer_data.gfd
import data --region=/BookOrder --member=server1 --file=../data/bookorder_data.gfd
import data --region=/InventoryItem --member=server1 --file=../data/inventory_data.gfd