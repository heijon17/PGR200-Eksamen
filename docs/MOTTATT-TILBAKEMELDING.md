### Fått evaluering
(Vurdert 10.11.18)
Positivt:
* `mvn install`kjører knirkefritt, med grønne tester
* Testdekningen er forholdsvis god
* Koden er ryddig skrevet
* Koden er stort sett lett å lese

Problemer:
* Vi klarer ikke å kjøre server, får error: `no main manifest attribute`
* Server kjører ikke, og kommandoer printer stackTrace til bruker
* Vi er usikre på avhengigheten mellom server og client

Forslag til forbedring:
* Readme er vanskelig å lese
* Noen metoder og felter brukes aldri (IDE gjør dem grå). De bør enten brukes eller fjernes