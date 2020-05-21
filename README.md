# Hevery

Hevery allows you to write [Git Hooks](https://git-scm.com/docs/githooks) in JVM languages. 
Now there is support for the following ones:
- Java

It allows you to write readable and maintainable hooks in a programming language you are familiar with. 
And depending on the language they can be even statically typed.

## Supported Hooks

- `commit-msg`
- `pre-commit`
- `prepare-commit-msg`

## Example

Have a look at the `prepare-commit-msg` hook implemented in Hevery:

```java
public class PrepareCommitMsg implements Hooks.PrepareCommitMsg {

    @Override
    public String onPrepareCommitMsg(
            String message,
            @Nullable CommitSource source,
            @Nullable String sha1) throws HookFailedException {
        return message;
    }

}
```

You do not need to read or write a file with a commit message. Also, @Nullable annotation explicitly shows what 
arguments are optional.

## Hooks directory structure

- `*.*` - regular hook files in their original shell-script format
- `hevery` - root folder of Hevery hooks
    - `bin` - where the main `.jar` lives that does all the magic
    - `build` - hooks compiled into `.class` files
    - `libs` - the directory for all third-party libraries to make you hooks even more useful
    - `*.java` - your hooks written in JVM language
    
## Hooks installation

Download `hevery-installer.jar` and run with the single argument referencing path to the root of target git repository:

```shell script
java -jar hevery-installer.jar /Users/developer/project
``` 

**Be careful**, the installer will overwrite shell-scripts of supported hooks!

## License

Copyright (c) 2020 Daniil Popov

Licensed under the [MIT](LICENSE) License.