package resources;

public class ResourceServerController implements ResourceServerControllerMBean {
    private final TestResourceI resourceServer;

    public ResourceServerController(TestResourceI resourceServer) {
        this.resourceServer = resourceServer;
    }

    @Override
    public String getname() {
        return resourceServer.getName();
    }

    @Override
    public int getage() {
        return resourceServer.getAge();
    }

}

