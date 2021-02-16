package com.twofactorauth.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "Users")
public class UserModel extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,updatable = false)
    Long id;

    @Column(unique = true)
    String username;

    @Column
    String pwd;

    /**
     * Updated by android device
     */
    @Column(unique = true)
    String oneSignalToken;

    /**
     * True : User confirmed
     * False: User declined
     */
    @Column
    Boolean isAllowed;

    /**
     * True: Two factor enabled
     * False: Two factor disabled
     */
    @Column
    Boolean isTwoFactorEnabled;

    /**
     * false for pending, true for success
     */
    @Column
    Boolean requestStatus;
}
