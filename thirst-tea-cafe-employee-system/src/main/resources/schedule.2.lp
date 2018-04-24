% ~~~~~~~~Logic~~~~~~~~

% ***DEFINE***

% Schedule between MinEmp and MaxEmp employees for each shift
EmpNum{ schedule(D,S,E,TimeFrom,TimeTo,H) : employee(E) }EmpNum :- shift(D,S,EmpNum,TimeFrom,TimeTo,H).

% ***GENERATE***

making_food(D,TimeFrom,TimeTo,E) :- schedule(D,_,E,TimeFrom,TimeTo,_), makes_food(E), not making_drinks(D,_,_,E). % Employee makes food for timerange if they aren't making drinks for the day
making_drinks(D,TimeFrom,TimeTo,E) :- schedule(D,_,E,TimeFrom,TimeTo,_), makes_drinks(E), not making_food(D,_,_,E).

drink_block_covered(D,TimeFrom,TimeTo) :- making_drinks(D,TimeFrom,TimeTo,_), day_range(D,TimeFrom,_).
drink_block_covered(D,TimeFrom,TimeTo) :- making_drinks(D,TimeFrom,TimeTo,_), #max{ TimeTo2,D : drink_block_covered(D,_,TimeTo2) } >= TimeFrom.
drink_min_covered(D,MinTimeFrom) :- MinTimeFrom = #min{ TimeFrom,D : drink_block_covered(D,TimeFrom,_) }, day(D).
drink_max_covered(D,MaxTimeTo) :- MaxTimeTo = #max{ TimeTo,D : drink_block_covered(D,_,TimeTo) }, day(D).

food_block_covered(D,TimeFrom,TimeTo) :- making_food(D,TimeFrom,TimeTo,_), day_range(D,TimeFrom,_).
food_block_covered(D,TimeFrom,TimeTo) :- making_food(D,TimeFrom,TimeTo,_), #max{ TimeTo2,D : food_block_covered(D,_,TimeTo2) } >= TimeFrom.
food_min_covered(D,MinTimeFrom) :- MinTimeFrom = #min{ TimeFrom,D : food_block_covered(D,TimeFrom,_) }, day(D).
food_max_covered(D,MaxTimeTo) :- MaxTimeTo = #max{ TimeTo,D : food_block_covered(D,_,TimeTo) }, day(D).

admin_block_covered(D,TimeFrom,TimeTo) :- schedule(D,_,E,TimeFrom,TimeTo,_), is_admin(E), day_range(D,TimeFrom,_).
admin_block_covered(D,TimeFrom,TimeTo) :- schedule(D,_,E,TimeFrom,TimeTo,_), is_admin(E), #max{ TimeTo2,D : admin_block_covered(D,_,TimeTo2) } >= TimeFrom.
admin_min_covered(D,MinTimeFrom) :- MinTimeFrom = #min{ TimeFrom,D : admin_block_covered(D,TimeFrom,_) }, day(D).
admin_max_covered(D,MaxTimeTo) :- MaxTimeTo = #max{ TimeTo,D : admin_block_covered(D,_,TimeTo) }, day(D).

lift_block_covered(D,TimeFrom,TimeTo) :- schedule(D,_,E,TimeFrom,TimeTo,_), can_lift(E), day_range(D,TimeFrom,_).
lift_block_covered(D,TimeFrom,TimeTo) :- schedule(D,_,E,TimeFrom,TimeTo,_), can_lift(E), #max{ TimeTo2,D : lift_block_covered(D,_,TimeTo2) } >= TimeFrom.
lift_min_covered(D,MinTimeFrom) :- MinTimeFrom = #min{ TimeFrom,D : lift_block_covered(D,TimeFrom,_) }, day(D).
lift_max_covered(D,MaxTimeTo) :- MaxTimeTo = #max{ TimeTo,D : lift_block_covered(D,_,TimeTo) }, day(D).

% making_food(D,TimeFrom,TimeTo,E) :- schedule(D,_,E,TimeFrom,TimeTo,_), makes_food(E).

hours_worked(E,T) :- T = #sum{ H,D,S : schedule(D,S,E,_,_,H) }, employee(E).
% admin_count(D,S,C) :- C = #count{ D,S,E : schedule(D,S,E,_), is_admin(E) }, shift(D,S,_,_).
% lift_count(D,S,C) :- C = #count{ D,S,E : schedule(D,S,E,_), can_lift(E) }, shift(D,S,_,_).
% food_maker_count(D,S,C) :- C = #count{ D,S,E : schedule(D,S,E,_), making_food(D,S,E) }, shift(D,S,_,_).
% drink_maker_count(D,S,C) :- C = #count{ D,S,E : schedule(D,S,E,_), making_drinks(D,S,E) }, shift(D,S,_,_).
shifts_in_day_count(D,E,C) :- C = #count{ D,S,E : schedule(D,S,E,_,_,_), schedule(D,S2,E2,_,_,_), E = E2 }, shift(D,_,_,_,_,_), employee(E).

% ***TEST***

% Remove answer sets that have employees scheduled more than 40 hours
% :- hours_worked(_,T), T > 40.

% Remove answer sets that have less than 1 admin
% :- admin_count(_,_,C), C < 1.

% Remove answer sets that have less than 1 lifter
% :- lift_count(_,_,C), C < 1.

% Remove answer sets that have less than 1 scheduled to make food
% :- food_maker_count(_,_,C), C < 1.

% Remove answer sets that have less than 1 scheduled to make drinks
% :- drink_maker_count(_,_,C), C < 1.

% Remove answer sets that have min drink covered time greater than day start time
:- drink_min_covered(D,TimeFrom), day_range(D,TimeFrom2,_), TimeFrom > TimeFrom2.
% Remove answer sets that have max drink covered time less than day end time
:- drink_max_covered(D,TimeTo), day_range(D,_,TimeTo2), TimeTo < TimeTo2.

% Remove answer sets that have min food covered time greater than day start time
:- food_min_covered(D,TimeFrom), day_range(D,TimeFrom2,_), TimeFrom > TimeFrom2.
% Remove answer sets that have max food covered time less than day end time
:- food_max_covered(D,TimeTo), day_range(D,_,TimeTo2), TimeTo < TimeTo2.

% Remove answer sets that have min admin covered time greater than day start time
:- admin_min_covered(D,TimeFrom), day_range(D,TimeFrom2,_), TimeFrom > TimeFrom2.
% Remove answer sets that have max admin covered time less than day end time
:- admin_max_covered(D,TimeTo), day_range(D,_,TimeTo2), TimeTo < TimeTo2.

% Remove answer sets that have min lift covered time greater than day start time
:- lift_min_covered(D,TimeFrom), day_range(D,TimeFrom2,_), TimeFrom > TimeFrom2.
% Remove answer sets that have max lift covered time less than day end time
:- lift_max_covered(D,TimeTo), day_range(D,_,TimeTo2), TimeTo < TimeTo2.

% Remove answer sets that have employees scheduled when they aren't available
:- schedule(D,S,E,_,_,_), not available(E,D,S).

% Remove answer sets that have non-admin employees scheduled on admin only shifts
:- schedule(D,S,E,_,_,_), admin_only_shift(D,S), not is_admin(E).

% Remove answer sets that have employees working more than 1 shift on the same day
:- shifts_in_day_count(_,E,C), C > 1.

% #minimize { T,E : hours_worked(E,T) }.
% #maximize { T,E : hours_worked(E,T) }.
% #minimize { T,E : hours_worked(E,T), is_admin(E) }.

#show schedule/6.
% #show hours_worked/2.
% #show making_drinks/4.
% #show making_food/4.
% #show drink_block_covered/3.
% #show food_block_covered/3.
% #show admin_block_covered/3.
% #show lift_block_covered/3.
% #show makes_drinks/1.
% #show shifts_in_day_count/3.

