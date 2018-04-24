% ~~~~~~~~Data Set~~~~~~~~
day(monday).

% SHIFTS
% shift(Day,Shift, MinEmp,MaxEmp, TimeFrom,TimeTo, Hours)
shift(monday,1, 1,1, 22,44, 11).
shift(monday,2, 1,1, 22,34, 6).
shift(monday,3, 2,2, 34,44, 5).
day_range(monday,22,44).

shift(tuesday,1, 1,1, 22,44, 11).
shift(tuesday,2, 1,1, 22,44, 11).
shift(tuesday,3, 1,1, 22,34, 6).
shift(tuesday,4, 1,1, 34,44, 5).
day_range(tuesday,22,44).

shift(wednesday,1, 2,2, 22,44, 11).
shift(wednesday,2, 1,1, 22,44, 11).
day_range(wednesday,22,44).

shift(thursday,1, 1,1, 22,44, 11).
shift(thursday,2, 1,1, 22,44, 11).
shift(thursday,3, 1,1, 34,44, 5).
day_range(thursday,22,44).

shift(friday,1, 1,1, 22,46, 12).
shift(friday,2, 1,1, 22,46, 12).
shift(friday,3, 1,1, 34,46, 6).
day_range(friday,22,46).

shift(saturday,1, 1,1, 22,46, 12).
shift(saturday,2, 1,1, 22,34, 6).
shift(saturday,3, 2,2, 34,46, 6).
day_range(saturday,22,46).

shift(sunday,1, 1,1, 22,42, 10).
shift(sunday,2, 1,1, 22,42, 10).
shift(sunday,3, 1,1, 32,42, 5).
day_range(sunday,22,42).

% EMPLOYEES
employee(jp;ef;bl;js;rs;tp;ww;id).

% available(Employee,Day,Shift)
available(jp,monday,3).
available(ef,monday,1..3).
available(bl,monday,3).
available(js,monday,2).
available(rs,monday,3).
available(tp,monday,1..3).
available(ww,monday,1..3).
available(id,monday,1..3).

available(ef,tuesday,1..4).
available(bl,tuesday,4).
available(js,tuesady,3).
available(rs,tuesday,1..4).
available(tp,tuesday,1..4).
available(ww,tuesday,1..4).
available(id,tuesday,1..4).

available(ef,wednesday,1..2).
available(js,wednesday,1..2).
available(tp,wednesday,1..2).
available(ww,wednesday,1..2).
available(id,wednesday,1..2).

available(ef,thursday,1..3).
available(js,thursday,1..3).
available(rs,thursday,1..3).
available(tp,thursday,1..3).
available(ww,thursday,1..3).
available(id,thursday,1..3).

available(ef,friday,1..3).
available(bl,friday,1..3).
available(tp,friday,1..3).
available(ww,friday,1..3).
available(id,friday,1..3).

available(jp,saturday,2).
available(ef,saturday,1..3).
available(bl,saturday,1..3).
available(rs,saturday,1..3).
available(tp,saturday,1..3).
available(ww,saturday,1..3).
available(id,saturday,1..3).

available(jp,sunday,1..3).
available(ef,sunday,1..3).
available(bl,sunday,1..3).
available(tp,sunday,1..3).
available(ww,sunday,1..3).
available(id,sunday,1..3).

is_admin(tp;ww;id).
can_lift(jp;bl;js;tp).
makes_food(tp;ww;id).
makes_drinks(jp;ef;bl;js;tp;ww;id).

admin_only_shift(monday,1).
admin_only_shift(tuesday,1).
admin_only_shift(wednesday,1).
admin_only_shift(thursday,1).
admin_only_shift(friday,1).
admin_only_shift(saturday,1).
admin_only_shift(sunday,1).
