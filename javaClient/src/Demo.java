import java.util.TreeMap;

import com.i2.ApiModuleCenter;
import com.i2.Module.*;


public class Demo {
    public static void main(String[] args) {
        TreeMap<String, Object> config = new TreeMap<String, Object>();

        config.put("token", "j6IaMfFE2KJPAH7y");
        config.put("SecretKey", "nGzbTNgy5DW0qiPoUmD3YWzOABn3ftfz");

        ApiModuleCenter module = new ApiModuleCenter(new I2(), config);
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        try {
            params.put("username", "admin");
            System.out.println("\n");
            System.out.println(module.call("User.Baseinfo", params));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
