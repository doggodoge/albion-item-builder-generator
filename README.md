Albion Online Item Builder Generator ⚙

This project will generate a kotlin file for the albion online data project API shim. Example use of generated code:

```kotlin
val itemBuilder = AlbionItemBuilder() // output: "T2_BAG,T4_MINERS_CAP"
	.bag(Tier.TWO)
	.minersCap(Tier.FOUR)
	.build()
```

- [x] Find lightweight API for code generation
- [x] Produce some valid code as proof of concept
- [ ] Generate a complete, working class that's fit for purpose
