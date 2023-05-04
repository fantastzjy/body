create table if not exists c_package
(
    package_id  bigint auto_increment
        primary key,
    type        int            null,
    name        varchar(50)    null,
    price       decimal(10, 4) null,
    tags        text           null,
    notion      text           null,
    extra       varchar(200)   null,
    is_valid    int            null,
    is_del      int            null,
    create_by   varchar(50)    null,
    create_time datetime       null,
    update_by   varchar(50)    null,
    update_time datetime       null
);

create table if not exists c_package_project_relation
(
    id           bigint auto_increment
        primary key,
    package_id   bigint                 null,
    project_id   bigint                 null,
    package_name varchar(50) default '' not null,
    project_name varchar(50) default '' not null,
    sort         int                    null,
    is_valid     int                    null,
    is_del       int                    null,
    create_by    varchar(50)            null,
    create_time  datetime               null,
    update_by    varchar(50)            null,
    update_time  datetime               null
);

create table if not exists c_project
(
    project_id  bigint auto_increment
        primary key,
    type        int            default 0      not null,
    name        varchar(50)    default ''     not null,
    room        varchar(50)    default ''     not null,
    price       decimal(10, 4) default 0.0000 not null,
    notion      text                          not null,
    is_notion   int            default 0      not null,
    unit        varchar(50)    default ''     not null,
    scope_min   decimal(10, 4) default 0.0000 not null,
    scope_max   decimal(10, 4) default 0.0000 not null,
    start_time  datetime                      null,
    end_time    datetime                      null,
    extra       varchar(200)                  null,
    is_valid    int                           null,
    is_del      int                           null,
    create_by   varchar(50)                   null,
    create_time datetime                      null,
    update_by   varchar(50)                   null,
    update_time datetime                      null
);

create table if not exists c_tips
(
    id          bigint auto_increment
        primary key,
    question    varchar(50)   null,
    answer      varchar(1000) null,
    is_valid    int           null,
    is_del      int           null,
    create_by   varchar(50)   null,
    create_time datetime      null,
    update_by   varchar(50)   null,
    update_time datetime      null
);

create table if not exists m_check
(
    check_id      bigint auto_increment
        primary key,
    member_id     bigint        default 0  not null comment '用户 id',
    member_name   varchar(50)   default '' not null comment '用户昵称',
    date          datetime                 not null comment '预约日期',
    package_id    bigint        default 0  not null comment '体检套餐',
    project_ids   varchar(200)  default '' not null comment '加项项目列表',
    checked_ids   varchar(200)  default '' not null comment '已体检项目 id 列表',
    status        int           default 0  not null comment '体检状态',
    exception_ids varchar(200)  default '' not null comment '异常项目 id列表',
    report        varchar(1000) default '' not null comment '体检报告',
    report_by     varchar(50)   default '' not null comment '体检报告出具医生',
    is_valid      int           default 0  not null comment '是否有效',
    is_del        int           default 0  not null comment '是否删除',
    create_by     varchar(50)   default '' not null comment '创建人',
    create_time   datetime                 not null comment '创建时间',
    update_by     varchar(50)   default '' not null comment '更新人',
    update_time   datetime                 not null comment '更新时间'
);

create table if not exists m_check_project
(
    id           bigint auto_increment
        primary key,
    project_id   bigint           null comment '项目 id',
    check_id     bigint default 0 not null comment '体检id',
    value        decimal(10, 4)   null comment '检查值',
    report       varchar(1000)    null comment '报告',
    check_by     varchar(50)      null comment '检查人',
    check_time   varchar(50)      null comment '检查时间',
    is_exception int              null comment '是否异常',
    is_valid     int              null comment '是否有效',
    is_del       int              null comment '是否删除',
    create_by    varchar(50)      null comment '创建者',
    create_time  datetime         null comment '创建时间',
    update_by    varchar(50)      null comment '更新者',
    update_time  datetime         null comment '更新时间'
);

create table if not exists m_check_status
(
    id                 bigint auto_increment
        primary key,
    member_id          bigint       default 0  not null comment '会员id',
    member_name        varchar(200)            not null comment '会员昵称',
    check_id           bigint       default 0  not null comment '体检id',
    check_name         varchar(200)            not null comment '体检套餐名称',
    check_project_id   bigint                  null comment '体检项目id',
    check_project_name varchar(200)            null comment '体检项目名称',
    status             int          default 0  not null comment '状态 0 待体检，1 已体检',
    status_des         varchar(200)            not null,
    notion             varchar(200) default '' not null comment '注意事项',
    sort               int          default 0  not null comment '顺序',
    room               varchar(200)            not null,
    is_valid           int                     null comment '是否有效',
    is_del             int                     null comment '是否删除',
    create_by          varchar(50)             null comment '创建者',
    create_time        datetime                null comment '创建时间',
    update_by          varchar(50)             null comment '更新者',
    update_time        datetime                null comment '更新时间'
);

create table if not exists pms_attr
(
    attr_id      bigint auto_increment comment '属性id'
        primary key,
    attr_name    char(30)     null comment '属性名',
    search_type  tinyint      null comment '是否需要检索[0-不需要，1-需要]',
    value_type   tinyint      null comment '值类型[0-为单个值，1-可以选择多个值]',
    icon         varchar(255) null comment '属性图标',
    value_select char(255)    null comment '可选值列表[用逗号分隔]',
    attr_type    tinyint      null comment '属性类型[0-销售属性，1-基本属性',
    enable       bigint       null comment '启用状态[0 - 禁用，1 - 启用]',
    catelog_id   bigint       null comment '所属分类',
    show_desc    tinyint      null comment '快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整'
)
    comment '商品属性';

create table if not exists pms_attr_attrgroup_relation
(
    id            bigint auto_increment comment 'id'
        primary key,
    attr_id       bigint null comment '属性id',
    attr_group_id bigint null comment '属性分组id',
    attr_sort     int    null comment '属性组内排序'
)
    comment '属性&属性分组关联';

create table if not exists pms_attr_group
(
    attr_group_id   bigint auto_increment comment '分组id'
        primary key,
    attr_group_name char(20)     null comment '组名',
    sort            int          null comment '排序',
    descript        varchar(255) null comment '描述',
    icon            varchar(255) null comment '组图标',
    catelog_id      bigint       null comment '所属分类id'
)
    comment '属性分组';

create table if not exists pms_brand
(
    brand_id     bigint auto_increment comment '品牌id'
        primary key,
    name         char(50)      null comment '品牌名',
    logo         varchar(2000) null comment '品牌logo地址',
    descript     longtext      null comment '介绍',
    show_status  tinyint       null comment '显示状态[0-不显示；1-显示]',
    first_letter char          null comment '检索首字母',
    sort         int           null comment '排序'
)
    comment '品牌';

create table if not exists pms_category
(
    cat_id        bigint auto_increment comment '分类id'
        primary key,
    name          char(50)  null comment '分类名称',
    parent_cid    bigint    null comment '父分类id',
    cat_level     int       null comment '层级',
    show_status   tinyint   null comment '是否显示[0-不显示，1显示]',
    sort          int       null comment '排序',
    icon          char(255) null comment '图标地址',
    product_unit  char(50)  null comment '计量单位',
    product_count int       null comment '商品数量'
)
    comment '商品三级分类';

create index parent_cid
    on pms_category (parent_cid);

create table if not exists pms_category_brand_relation
(
    id           bigint auto_increment
        primary key,
    brand_id     bigint       null comment '品牌id',
    catelog_id   bigint       null comment '分类id',
    brand_name   varchar(255) null,
    catelog_name varchar(255) null
)
    comment '品牌分类关联';

create table if not exists pms_comment_replay
(
    id         bigint auto_increment comment 'id'
        primary key,
    comment_id bigint null comment '评论id',
    reply_id   bigint null comment '回复id'
)
    comment '商品评价回复关系';

create table if not exists pms_product_attr_value
(
    id         bigint auto_increment comment 'id'
        primary key,
    spu_id     bigint       null comment '商品id',
    attr_id    bigint       null comment '属性id',
    attr_name  varchar(200) null comment '属性名',
    attr_value varchar(200) null comment '属性值',
    attr_sort  int          null comment '顺序',
    quick_show tinyint      null comment '快速展示【是否展示在介绍上；0-否 1-是】'
)
    comment 'spu属性值';

create table if not exists pms_sku_images
(
    id          bigint auto_increment comment 'id'
        primary key,
    sku_id      bigint       null comment 'sku_id',
    img_url     varchar(255) null comment '图片地址',
    img_sort    int          null comment '排序',
    default_img int          null comment '默认图[0 - 不是默认图，1 - 是默认图]'
)
    comment 'sku图片';

create table if not exists pms_sku_info
(
    sku_id          bigint auto_increment comment 'skuId'
        primary key,
    spu_id          bigint         null comment 'spuId',
    sku_name        varchar(255)   null comment 'sku名称',
    sku_desc        varchar(2000)  null comment 'sku介绍描述',
    catalog_id      bigint         null comment '所属分类id',
    brand_id        bigint         null comment '品牌id',
    sku_default_img varchar(255)   null comment '默认图片',
    sku_title       varchar(255)   null comment '标题',
    sku_subtitle    varchar(2000)  null comment '副标题',
    price           decimal(18, 4) null comment '价格',
    sale_count      bigint         null comment '销量'
)
    comment 'sku信息';

create table if not exists pms_sku_sale_attr_value
(
    id         bigint auto_increment comment 'id'
        primary key,
    sku_id     bigint       null comment 'sku_id',
    attr_id    bigint       null comment 'attr_id',
    attr_name  varchar(200) null comment '销售属性名',
    attr_value varchar(200) null comment '销售属性值',
    attr_sort  int          null comment '顺序'
)
    comment 'sku销售属性&值';

create table if not exists pms_spu_comment
(
    id               bigint auto_increment comment 'id'
        primary key,
    sku_id           bigint        null comment 'sku_id',
    spu_id           bigint        null comment 'spu_id',
    spu_name         varchar(255)  null comment '商品名字',
    member_nick_name varchar(255)  null comment '会员昵称',
    star             tinyint(1)    null comment '星级',
    member_ip        varchar(64)   null comment '会员ip',
    create_time      datetime      null comment '创建时间',
    show_status      tinyint(1)    null comment '显示状态[0-不显示，1-显示]',
    spu_attributes   varchar(255)  null comment '购买时属性组合',
    likes_count      int           null comment '点赞数',
    reply_count      int           null comment '回复数',
    resources        varchar(1000) null comment '评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]',
    content          text          null comment '内容',
    member_icon      varchar(255)  null comment '用户头像',
    comment_type     tinyint       null comment '评论类型[0 - 对商品的直接评论，1 - 对评论的回复]'
)
    comment '商品评价';

create table if not exists pms_spu_images
(
    id          bigint auto_increment comment 'id'
        primary key,
    spu_id      bigint       null comment 'spu_id',
    img_name    varchar(200) null comment '图片名',
    img_url     varchar(255) null comment '图片地址',
    img_sort    int          null comment '顺序',
    default_img tinyint      null comment '是否默认图'
)
    comment 'spu图片';

create table if not exists pms_spu_info
(
    id              bigint auto_increment comment '商品id'
        primary key,
    spu_name        varchar(200)   null comment '商品名称',
    spu_description varchar(1000)  null comment '商品描述',
    catalog_id      bigint         null comment '所属分类id',
    brand_id        bigint         null comment '品牌id',
    weight          decimal(18, 4) null,
    publish_status  tinyint        null comment '上架状态[0 - 下架，1 - 上架]',
    create_time     datetime       null,
    update_time     datetime       null
)
    comment 'spu信息';

create table if not exists pms_spu_info_desc
(
    spu_id  bigint   not null comment '商品id'
        primary key,
    decript longtext null comment '商品介绍'
)
    comment 'spu信息介绍';

create table if not exists qrtz_calendars
(
    SCHED_NAME    varchar(120) not null,
    CALENDAR_NAME varchar(200) not null,
    CALENDAR      blob         not null,
    primary key (SCHED_NAME, CALENDAR_NAME)
)
    charset = utf8mb3;

create table if not exists qrtz_fired_triggers
(
    SCHED_NAME        varchar(120) not null,
    ENTRY_ID          varchar(95)  not null,
    TRIGGER_NAME      varchar(200) not null,
    TRIGGER_GROUP     varchar(200) not null,
    INSTANCE_NAME     varchar(200) not null,
    FIRED_TIME        bigint       not null,
    SCHED_TIME        bigint       not null,
    PRIORITY          int          not null,
    STATE             varchar(16)  not null,
    JOB_NAME          varchar(200) null,
    JOB_GROUP         varchar(200) null,
    IS_NONCONCURRENT  varchar(1)   null,
    REQUESTS_RECOVERY varchar(1)   null,
    primary key (SCHED_NAME, ENTRY_ID)
)
    charset = utf8mb3;

create index IDX_QRTZ_FT_INST_JOB_REQ_RCVRY
    on qrtz_fired_triggers (SCHED_NAME, INSTANCE_NAME, REQUESTS_RECOVERY);

create index IDX_QRTZ_FT_JG
    on qrtz_fired_triggers (SCHED_NAME, JOB_GROUP);

create index IDX_QRTZ_FT_J_G
    on qrtz_fired_triggers (SCHED_NAME, JOB_NAME, JOB_GROUP);

create index IDX_QRTZ_FT_TG
    on qrtz_fired_triggers (SCHED_NAME, TRIGGER_GROUP);

create index IDX_QRTZ_FT_TRIG_INST_NAME
    on qrtz_fired_triggers (SCHED_NAME, INSTANCE_NAME);

create index IDX_QRTZ_FT_T_G
    on qrtz_fired_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

create table if not exists qrtz_job_details
(
    SCHED_NAME        varchar(120) not null,
    JOB_NAME          varchar(200) not null,
    JOB_GROUP         varchar(200) not null,
    DESCRIPTION       varchar(250) null,
    JOB_CLASS_NAME    varchar(250) not null,
    IS_DURABLE        varchar(1)   not null,
    IS_NONCONCURRENT  varchar(1)   not null,
    IS_UPDATE_DATA    varchar(1)   not null,
    REQUESTS_RECOVERY varchar(1)   not null,
    JOB_DATA          blob         null,
    primary key (SCHED_NAME, JOB_NAME, JOB_GROUP)
)
    charset = utf8mb3;

create index IDX_QRTZ_J_GRP
    on qrtz_job_details (SCHED_NAME, JOB_GROUP);

create index IDX_QRTZ_J_REQ_RECOVERY
    on qrtz_job_details (SCHED_NAME, REQUESTS_RECOVERY);

create table if not exists qrtz_locks
(
    SCHED_NAME varchar(120) not null,
    LOCK_NAME  varchar(40)  not null,
    primary key (SCHED_NAME, LOCK_NAME)
)
    charset = utf8mb3;

create table if not exists qrtz_paused_trigger_grps
(
    SCHED_NAME    varchar(120) not null,
    TRIGGER_GROUP varchar(200) not null,
    primary key (SCHED_NAME, TRIGGER_GROUP)
)
    charset = utf8mb3;

create table if not exists qrtz_scheduler_state
(
    SCHED_NAME        varchar(120) not null,
    INSTANCE_NAME     varchar(200) not null,
    LAST_CHECKIN_TIME bigint       not null,
    CHECKIN_INTERVAL  bigint       not null,
    primary key (SCHED_NAME, INSTANCE_NAME)
)
    charset = utf8mb3;

create table if not exists qrtz_triggers
(
    SCHED_NAME     varchar(120) not null,
    TRIGGER_NAME   varchar(200) not null,
    TRIGGER_GROUP  varchar(200) not null,
    JOB_NAME       varchar(200) not null,
    JOB_GROUP      varchar(200) not null,
    DESCRIPTION    varchar(250) null,
    NEXT_FIRE_TIME bigint       null,
    PREV_FIRE_TIME bigint       null,
    PRIORITY       int          null,
    TRIGGER_STATE  varchar(16)  not null,
    TRIGGER_TYPE   varchar(8)   not null,
    START_TIME     bigint       not null,
    END_TIME       bigint       null,
    CALENDAR_NAME  varchar(200) null,
    MISFIRE_INSTR  smallint     null,
    JOB_DATA       blob         null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_TRIGGERS_ibfk_1
        foreign key (SCHED_NAME, JOB_NAME, JOB_GROUP) references qrtz_job_details (SCHED_NAME, JOB_NAME, JOB_GROUP)
)
    charset = utf8mb3;

create table if not exists qrtz_blob_triggers
(
    SCHED_NAME    varchar(120) not null,
    TRIGGER_NAME  varchar(200) not null,
    TRIGGER_GROUP varchar(200) not null,
    BLOB_DATA     blob         null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_BLOB_TRIGGERS_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
)
    charset = utf8mb3;

create index SCHED_NAME
    on qrtz_blob_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

create table if not exists qrtz_cron_triggers
(
    SCHED_NAME      varchar(120) not null,
    TRIGGER_NAME    varchar(200) not null,
    TRIGGER_GROUP   varchar(200) not null,
    CRON_EXPRESSION varchar(120) not null,
    TIME_ZONE_ID    varchar(80)  null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_CRON_TRIGGERS_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
)
    charset = utf8mb3;

create table if not exists qrtz_simple_triggers
(
    SCHED_NAME      varchar(120) not null,
    TRIGGER_NAME    varchar(200) not null,
    TRIGGER_GROUP   varchar(200) not null,
    REPEAT_COUNT    bigint       not null,
    REPEAT_INTERVAL bigint       not null,
    TIMES_TRIGGERED bigint       not null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_SIMPLE_TRIGGERS_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
)
    charset = utf8mb3;

create table if not exists qrtz_simprop_triggers
(
    SCHED_NAME    varchar(120)   not null,
    TRIGGER_NAME  varchar(200)   not null,
    TRIGGER_GROUP varchar(200)   not null,
    STR_PROP_1    varchar(512)   null,
    STR_PROP_2    varchar(512)   null,
    STR_PROP_3    varchar(512)   null,
    INT_PROP_1    int            null,
    INT_PROP_2    int            null,
    LONG_PROP_1   bigint         null,
    LONG_PROP_2   bigint         null,
    DEC_PROP_1    decimal(13, 4) null,
    DEC_PROP_2    decimal(13, 4) null,
    BOOL_PROP_1   varchar(1)     null,
    BOOL_PROP_2   varchar(1)     null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_SIMPROP_TRIGGERS_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
)
    charset = utf8mb3;

create index IDX_QRTZ_T_C
    on qrtz_triggers (SCHED_NAME, CALENDAR_NAME);

create index IDX_QRTZ_T_G
    on qrtz_triggers (SCHED_NAME, TRIGGER_GROUP);

create index IDX_QRTZ_T_J
    on qrtz_triggers (SCHED_NAME, JOB_NAME, JOB_GROUP);

create index IDX_QRTZ_T_JG
    on qrtz_triggers (SCHED_NAME, JOB_GROUP);

create index IDX_QRTZ_T_NEXT_FIRE_TIME
    on qrtz_triggers (SCHED_NAME, NEXT_FIRE_TIME);

create index IDX_QRTZ_T_NFT_MISFIRE
    on qrtz_triggers (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME);

create index IDX_QRTZ_T_NFT_ST
    on qrtz_triggers (SCHED_NAME, TRIGGER_STATE, NEXT_FIRE_TIME);

create index IDX_QRTZ_T_NFT_ST_MISFIRE
    on qrtz_triggers (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_STATE);

create index IDX_QRTZ_T_NFT_ST_MISFIRE_GRP
    on qrtz_triggers (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_GROUP, TRIGGER_STATE);

create index IDX_QRTZ_T_N_G_STATE
    on qrtz_triggers (SCHED_NAME, TRIGGER_GROUP, TRIGGER_STATE);

create index IDX_QRTZ_T_N_STATE
    on qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_STATE);

create index IDX_QRTZ_T_STATE
    on qrtz_triggers (SCHED_NAME, TRIGGER_STATE);

create table if not exists schedule_job
(
    job_id          bigint auto_increment comment '任务id'
        primary key,
    bean_name       varchar(200)  null comment 'spring bean名称',
    params          varchar(2000) null comment '参数',
    cron_expression varchar(100)  null comment 'cron表达式',
    status          tinyint       null comment '任务状态  0：正常  1：暂停',
    remark          varchar(255)  null comment '备注',
    create_time     datetime      null comment '创建时间'
)
    comment '定时任务';

create table if not exists schedule_job_log
(
    log_id      bigint auto_increment comment '任务日志id'
        primary key,
    job_id      bigint        not null comment '任务id',
    bean_name   varchar(200)  null comment 'spring bean名称',
    params      varchar(2000) null comment '参数',
    status      tinyint       not null comment '任务状态    0：成功    1：失败',
    error       varchar(2000) null comment '失败信息',
    times       int           not null comment '耗时(单位：毫秒)',
    create_time datetime      null comment '创建时间'
)
    comment '定时任务日志';

create index job_id
    on schedule_job_log (job_id);

create table if not exists sys_captcha
(
    uuid        char(36)   not null comment 'uuid'
        primary key,
    code        varchar(6) not null comment '验证码',
    expire_time datetime   null comment '过期时间'
)
    comment '系统验证码';

create table if not exists sys_config
(
    id          bigint auto_increment
        primary key,
    param_key   varchar(50)       null comment 'key',
    param_value varchar(2000)     null comment 'value',
    status      tinyint default 1 null comment '状态   0：隐藏   1：显示',
    remark      varchar(500)      null comment '备注',
    constraint param_key
        unique (param_key)
)
    comment '系统配置信息表';

create table if not exists sys_log
(
    id          bigint auto_increment
        primary key,
    username    varchar(50)   null comment '用户名',
    operation   varchar(50)   null comment '用户操作',
    method      varchar(200)  null comment '请求方法',
    params      varchar(5000) null comment '请求参数',
    time        bigint        not null comment '执行时长(毫秒)',
    ip          varchar(64)   null comment 'IP地址',
    create_date datetime      null comment '创建时间'
)
    comment '系统日志';

create table if not exists sys_menu
(
    menu_id   bigint auto_increment
        primary key,
    parent_id bigint       null comment '父菜单ID，一级菜单为0',
    name      varchar(50)  null comment '菜单名称',
    url       varchar(200) null comment '菜单URL',
    perms     varchar(500) null comment '授权(多个用逗号分隔，如：user:list,user:create)',
    type      int          null comment '类型   0：目录   1：菜单   2：按钮',
    icon      varchar(50)  null comment '菜单图标',
    order_num int          null comment '排序'
)
    comment '菜单管理';

create table if not exists sys_oss
(
    id          bigint auto_increment
        primary key,
    url         varchar(200) null comment 'URL地址',
    create_date datetime     null comment '创建时间'
)
    comment '文件上传';

create table if not exists sys_role
(
    role_id        bigint auto_increment
        primary key,
    role_name      varchar(100) null comment '角色名称',
    remark         varchar(100) null comment '备注',
    create_user_id bigint       null comment '创建者ID',
    create_time    datetime     null comment '创建时间'
)
    comment '角色';

create table if not exists sys_role_menu
(
    id      bigint auto_increment
        primary key,
    role_id bigint null comment '角色ID',
    menu_id bigint null comment '菜单ID'
)
    comment '角色与菜单对应关系';

create table if not exists sys_user
(
    user_id        bigint auto_increment
        primary key,
    username       varchar(50)  not null comment '用户名',
    password       varchar(100) null comment '密码',
    salt           varchar(20)  null comment '盐',
    email          varchar(100) null comment '邮箱',
    mobile         varchar(100) null comment '手机号',
    status         tinyint      null comment '状态  0：禁用   1：正常',
    create_user_id bigint       null comment '创建者ID',
    create_time    datetime     null comment '创建时间',
    constraint username
        unique (username)
)
    comment '系统用户';

create table if not exists sys_user_role
(
    id      bigint auto_increment
        primary key,
    user_id bigint null comment '用户ID',
    role_id bigint null comment '角色ID'
)
    comment '用户与角色对应关系';

create table if not exists sys_user_token
(
    user_id     bigint       not null
        primary key,
    token       varchar(100) not null comment 'token',
    expire_time datetime     null comment '过期时间',
    update_time datetime     null comment '更新时间',
    constraint token
        unique (token)
)
    comment '系统用户Token';

create table if not exists tb_user
(
    user_id     bigint auto_increment
        primary key,
    username    varchar(50) not null comment '用户名',
    mobile      varchar(20) not null comment '手机号',
    password    varchar(64) null comment '密码',
    create_time datetime    null comment '创建时间',
    constraint username
        unique (username)
)
    comment '用户';

create table if not exists undo_log
(
    id            bigint auto_increment
        primary key,
    branch_id     bigint       not null,
    xid           varchar(100) not null,
    context       varchar(128) not null,
    rollback_info longblob     not null,
    log_status    int          not null,
    log_created   datetime     not null,
    log_modified  datetime     not null,
    ext           varchar(100) null,
    constraint ux_undo_log
        unique (xid, branch_id)
)
    charset = utf8mb3;

