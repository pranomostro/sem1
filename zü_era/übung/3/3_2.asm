; Probleme bei der Implementierung:
; Wartezeiten kleiner als 5 Sekunden.
; Lösung: Einfach nicht beachten?
; Ist bei einer Waschmaschine sowieso
; nicht so relevant.
; Wartezeiten größer als 68 Jahre
; Lösung: Bis dahin 64-Bit register benutzen.

warte_n:

mov ebx, eax

warte_schleife:

cmp ebx, 5
jl ende
sub ebx, 5
;warte_5s
jmp warte_schleife

ende:
