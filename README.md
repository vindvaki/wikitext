# A super simple plaintext exporter for wikipedia dumps

## What?

A very simple program to extract plaintext from wikipedia dumps (seriously, it's just a few lines of code, all the heavy lifting is done by [bliki]).

- input: Path to a wikipedia dump (first argument to the command)
- output: The entire thing as plaintext (i.e. with all the markup stripped) on stdout

## Why?

Because the markup gets in your way if you just want to look at the words.

## Usage

```bash
wikitext PATH_TO_DUMP_FILE > PATH_TO_OUTPUT_FILE
```

You don't need to pipe stdout to a file, but given that the dump can be quite huge, you probably want to do something along those lines.


## Compiling and installing

```bash
gradle build
```

will result in a redistributable archives under `build/distributions`, whereas

```bash
gradle installDist
```

will install the distribution under `build/install/wikitext/`.

In any case, you just need to make sure that the `bin` folder of the
distribution is in your path in order to run `wikitext`.


[bliki]: https://bitbucket.org/axelclk/info.bliki.wiki
[gradle]: https://gradle.org
