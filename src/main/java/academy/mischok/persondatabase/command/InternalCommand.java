package academy.mischok.persondatabase.command;

import lombok.Data;

@Data
public class InternalCommand {

    private ICommand clazz;

    private String name;
    private String description;
    private String[] aliases;

}
