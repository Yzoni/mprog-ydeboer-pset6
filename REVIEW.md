# Peer Review 
##### mprog-ydeboer-pset6

###### Door Sjors Witteveen

## Names
Kleine typfout in BaseActivity (Listner)
private void setConnectedStatusListner() {

## Headers
Er zijn geen headers gebruikt.

Strings voor bijvoorbeeld Toasts staan direct in de code. Beter is om deze in strings.xml te zetten en deze vanuit de code aan te roepen (R.strings.blabla).

## Comments
Iets meer aandacht besteden aan de comments. Er is te weinig gebruik gemaakt van comments. Soms is het niet direct duidelijk wat methods doen. Een voorbeeld hiervan is:

Uit DetailActivity:
private void setFabStatus() {

Ook zijn sommige comments slordig of onduidelijk. Voorbeeld:

Uit MainActivity (typfouten en 'a other' moet natuurlijk another zijn):
/**
 * Swaps a the fragmen in the framelayout with a other fragment class
 *
 * @param fragmentClass
 */
private void handleFragmentSwap(Class fragmentClass) {

## Code
Eigenlijk vrij weinig/niks aan te merken op de code zelf. Zeer nette code.

Mooi hoe je eerst AppCompatActivity extend met BaseActivity en vervolgens deze weer extend in je Activities om dubbele code te vermijden.

## Android style
Iets wat mij opviel in de screenshots was het icoon dat gebruikt wordt in het menu (bij opsomming van Characters en Issues). In Android wordt dat icoon gebruikt als 'verzenden', in bijvoorbeeld mails of chat apps. In dit geval zou ik een ander icoon gebruiken. Ook zou ik een korte beschrijving (1-2 regel(s)) geven in de readme.