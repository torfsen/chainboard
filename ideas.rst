ChainBoard Ideas
################


Syntax
======

Each of the 8 keys is represented by a digit (0-9). A state can be described
using a sequence of key presses and releases. Since each key can be only
pressed or released, the key's digit denotes both kinds of events. So, for
example, the state ``0113`` represents the input sequence "press key 0, press
key 1, release key 1, press key 3". A ``*`` denotes the release of all
currently pressed keys in any order. The state where no key is pressed is
denoted using ``~``.

Keys are numbered from the top left (0) to the lower right (7) in a
left-to-right, top-down manner.

A state transition is denoted by the original state ``O``, the emitted key
``K`` and the new state ``N``. Hence, ``O --[K]--> N`` means that state ``O``
transitions into state ``N`` and that the key ``K`` is emitted during that
transition.


Rules for Transitions
=====================

For a state transition ``O --> N`` the following rule needs to hold to allow a
usable interface:

* *Preservation of pressed keys:* The keys which are pressed in ``O`` must also
  be pressed in ``N``.


Protypical Mapping
==================

Cursor Movements
----------------

Cursor movements are required frequently. Hence they must be accessible using
short chains. The lower left key (6) is the trigger, followed by a single key
toggle on the right::

    611 --[UP   ]--> 6
    633 --[DOWN ]--> 6
    655 --[LEFT ]--> 6
    677 --[RIGHT]--> 6

By looping back to state ``6``, repeated key toggles on the right while holding
key 6 cause repeated movements of the cursor. For example, the key sequence
``65555116*`` moves the cursor two characters to the left and one row up.

A similar mechanism is used for the larger cursor movements ``PGUP`` and
``PGDOWN``, as well as for the pseudo-cursor movements ``BACKSPACE`` and
``DEL``::

    6166 --[PGUP     ]--> 61
    6366 --[PGDOWN   ]--> 63
    6566 --[BACKSPACE]--> 65
    6766 --[DEL      ]--> 67

Here, the pattern is that a certain movement is selected by a combination of
key 6 and a right-hand key. The movement is then triggered using a toggle of
key 6. For example, ``65666666*`` is three backspaces.


Lowercase Letters
-----------------

Every lowercase letter can be accessed using two key toggles: Combinations
start with a right-hand key and are followed by a left-hand key. The order in
which these keys are then released is significant. This leads to
``4 * 4 * 2 = 32`` combinations, enough for the 26 lowercase letters.

An explicit mapping for the lowercase letters hasn't been designed, yet.


Uppercase Letters
-----------------

Uppercase letters are accessed the same as lowercase letters, aside from an
additional key press of key 4. That is, ``414010`` is the uppercase equivalent
of ``1010``.


Special Characters
------------------

Special characters are accessed like uppercase letters, but using key 2 instead
of key 4 at the beginning.


Whitespace and Numbers
----------------------

To allow for fast access to whitespace, the following rules are used (similar
to cursor movement)::

    033 --[NEWLINE]--> 0
    055 --[TAB    ]--> 0
    077 --[SPACE  ]--> 0

The remaining combination starting with key 0, ``01`` is used to access
numbers. After the ``01`` initialization, numbers use a two-key sequence,
starting on the right side::

    010011* --[0]--> ~
    010013* --[1]--> ~
    ...
    010211* --[4]--> ~
    ...
    010611* --[c]--> ~
    ...
    010617* --[f]--> ~

Since there are 16 slots, we use the full hexadecimal range.



TODO
----

* The keys ``HOME``/``POS1`` and ``END``
* A usage for the short sequences ``00``, ``11``, ..., ``77``
* Idea: Use a compose-key approach for umlauts, etc.